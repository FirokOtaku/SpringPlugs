package firok.spring.plugs.controller;

import firok.topaz.spring.Ret;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * shutdown api
 * @since 0.8.0-j21
 * */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@ConditionalOnExpression("${firok.spring.plugs.shutdown.enable:false}")
@RequestMapping("/plugs/shutdown")
@RestController
public class ShutdownController implements ApplicationContextAware
{
    private AbstractApplicationContext context;
    @Override
    public void setApplicationContext(@NotNull ApplicationContext context)
    {
        if(context instanceof AbstractApplicationContext c) this.context = c;
        else throw new IllegalArgumentException("param is not instance of AbstractApplicationContext");
    }

    public interface ShutdownAuthHook
    {
        boolean auth(HttpServletRequest request, HttpServletResponse response, String token);
    }

    @Autowired(required = false)
    private ShutdownAuthHook hook;

    @Getter
    private String authKey;

    /**
     * 生成一个锁文件名称.
     * 文件名格式为: {start datetime}|{pid}|{random key}.lock
     * */
    private static String nextLockName()
    {
        var dt = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        var now = dt.format(new Date());
        var pid = ProcessHandle.current().pid();
        var key = UUID.randomUUID().toString().substring(0, 13);
        return now + '|' + pid + '|' + key + ".lock";
    }

    @PostConstruct
    public void postHook()
    {
        if(hook != null) return;

        authKey = nextLockName();
    }

    @RequestMapping("/execute")
    public Ret<?> execute(
            @RequestParam("pass") String pass,
            HttpServletRequest request,
            HttpServletResponse response
    )
    {
        boolean hasAuth;
        if(hook != null)
        {
            try
            {
                hasAuth = hook.auth(request, response, pass);
            }
            catch (Exception any)
            {
                hasAuth = false;
            }
        }
        else
        {
            hasAuth = Objects.equals(authKey, pass);
        }

        if(hasAuth)
        {
            context.close();
            return Ret.success();
        }
        else
        {
            return Ret.fail("! - YOU - SHALL - NOT - PASS - !");
        }
    }
}
