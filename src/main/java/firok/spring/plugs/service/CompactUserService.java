package firok.spring.plugs.service;

import firok.spring.plugs.PlugsExceptions;
import firok.spring.plugs.bean.UserBean;
import firok.spring.plugs.bean.query.QUserBean;
import firok.spring.plugs.config.UserConfig;
import firok.spring.plugs.util.TableUtil;
import firok.topaz.general.CodeException;
import io.ebean.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.*;

import static firok.topaz.general.Collections.isEmpty;
import static firok.topaz.general.Collections.sizeOf;

/**
 * 用户数据相关
 * @implNote 除去第一步获取用户数据可以根据用户名, 其余接口全部基于 id
 * */
@Service
@ConditionalOnBean(UserConfig.class)
public class CompactUserService extends AbstractCompactService
{
    @Override
    protected final String sqlTableName()
    {
        return UserBean.TableName;
    }

    @Override
    protected String sqlShowTables()
    {
        return TableUtil.SqlShowTablesMysql;
    }

    @Override
    protected String sqlCreateTable()
    {
        return UserBean.CreateTableMysql;
    }

    /**
     * 根据 id 获取用户数据
     * */
    public UserBean getUserById(String id)
    {
        return new QUserBean().id.eq(id).findOne();
    }
    /**
     * 根据用户名获取用户数据
     * */
    public UserBean getUserByUsername(String username)
    {
        PlugsExceptions.UsernameNotNull.maybe(username == null);
        return new QUserBean().username.eq(username).findOne();
    }
    /**
     * 根据 id 列表获取用户数据
     * */
    public List<UserBean> getUserByIds(Collection<String> ids)
    {
        if(isEmpty(ids)) return new ArrayList<>(0);
        return new QUserBean().id.in(ids).findList();
    }

    /**
     * 根据 id 删除用户数据
     * */
    public boolean deleteUserById(String id)
    {
        return new QUserBean().id.eq(id).delete() > 0;
    }
    /**
     * 根据 id 批量删除用户数据
     * */
    public boolean deleteUserByIds(Collection<String> ids)
    {
        return !isEmpty(ids) && new QUserBean().id.in(ids).delete() == sizeOf(ids);
    }

    /**
     * 根据 id 更新用户数据
     * */
    public boolean updateUserById(UserBean user)
    {
        DB.update(user);
        return true; // todo handle exception
    }

    /**
     * 校验用户名密码是否可用
     * @return 查询到的用户数据
     * @throws CodeException 如果遇到问题则抛出
     * @apiNote 这个接口本身不会对获取到的用户数据做去敏处理, 调用者需要自行将密钥等字段清空
     * */
    public UserBean auth(String username, String password)
    {
        PlugsExceptions.UsernameNotNull.maybe(username == null);
        PlugsExceptions.PasswordNotMatch.maybe(password == null);
        var user = getUserByUsername(username);
        PlugsExceptions.UserNotFound.maybe(user == null);
        assert user != null;
        PlugsExceptions.PasswordNotMatch.maybe(!Objects.equals(password, user.getPassword()));
        return user;
    }

    /**
     * 创建一个用户, 这会赋予一个随机的盐值
     * */
    public UserBean createUser(String username, String nickname, String password)
    {
        PlugsExceptions.UsernameNotNull.maybe(username == null);
        PlugsExceptions.NicknameNotNull.maybe(nickname == null);
        PlugsExceptions.PasswordNotMatch.maybe(password == null);
        PlugsExceptions.UsernameAlreadyExist.maybe(
                new QUserBean().username.eq(username).findCount() > 0
        );

        var userId = UUID.randomUUID().toString();
        var cookieToken = UUID.randomUUID().toString().substring(0, 8);
        var user = new UserBean();
        user.setId(userId);
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setTokenCookie(cookieToken);
        DB.save(user);

        return user;
    }

    @Autowired(required = false)
    CompactEncryptService enc;

    /**
     * 生成一个 token, 用来在 cookie 里当鉴权 token
     * @apiNote 调用这个接口需要系统引入了 {@link CompactEncryptService} 组件
     * */
    public String generateCookieToken(UserBean user, long timeLast)
    {
       try
       {
           var cookieToken = user.getTokenCookie();
           var timeNow = System.currentTimeMillis();
           var timeEnd = timeNow + timeLast;
           var userId = user.getId();
           var tokenRaw = "ct," + userId + "," + cookieToken + "," + timeEnd;
           var tokenSet = enc.enc(tokenRaw);
           return String.join(",", tokenSet); // token value
       }
       catch (Exception any)
       {
           return PlugsExceptions.WriteCookieTokenFail.occur(any);
       }
    }

    /**
     * 对 cookie 中的 token 进行鉴权
     * */
    public UserBean checkCookieToken(String tokenValue)
    {
        try
        {
            var tokenSet = tokenValue.split(",");
            var tokenRaw = enc.dec(tokenSet);
            var tokenSplit = tokenRaw.split(",");
            PlugsExceptions.TokenFormatError.maybe(tokenSplit.length != 4);
            String tokenType = tokenSplit[0], userId = tokenSplit[1], cookieToken = tokenSplit[2], timeEnd = tokenSplit[3];
            PlugsExceptions.TokenTypeError.maybe(!"ct".equals(tokenType));
            var user = getUserById(userId);
            PlugsExceptions.UserNotFound.maybe(user == null);
            assert user != null;
            PlugsExceptions.TokenNotMatch.maybe(!Objects.equals(cookieToken, user.getTokenCookie()));
            PlugsExceptions.TokenExpired.maybe(Long.parseLong(timeEnd) > System.currentTimeMillis());
            return user;
        }
        catch (Exception any)
        {
            return PlugsExceptions.ReadCookieTokenFail.occur(any);
        }
    }

    /**
     * 更新用户的 token
     * */
    public boolean updateCookieToken(String id)
    {
        var user = getUserById(id);
        PlugsExceptions.UserNotFound.maybe(user == null);
        assert user != null;
        user.setTokenCookie(UUID.randomUUID().toString().substring(0, 8));
        DB.update(user);
        return true; // todo handle exception
    }
}
