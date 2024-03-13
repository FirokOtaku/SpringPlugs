package firok.spring.plugs.service;

import firok.spring.plugs.PlugsExceptions;
import firok.spring.plugs.bean.UserBean;
import firok.spring.plugs.bean.query.QUserBean;
import firok.spring.plugs.config.UserConfig;
import firok.spring.plugs.util.TableUtil;
import firok.topaz.general.CodeException;
import firok.topaz.general.Encrypts;
import io.ebean.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import java.nio.charset.StandardCharsets;
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

    @Override
    protected boolean shouldAutoCreateTable()
    {
        return config.getAutoCreateTable();
    }

    @Autowired
    UserConfig config;

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

    private static final String HMAC_KEY = "6dcf3020-e8b5648dc4b2";
    public static final Mac HMAC_MAC = Encrypts.initHMACMac(HMAC_KEY);
    /**
     * 校验用户名密码是否可用
     * @return 查询到的用户数据
     * @throws CodeException 如果遇到问题则抛出
     * @apiNote 这个接口本身不会对获取到的用户数据做去敏处理, 调用者需要自行将密钥等字段清空
     * */
    public UserBean checkPassword(String username, String password)
    {
        PlugsExceptions.UsernameNotNull.maybe(username == null);
        PlugsExceptions.PasswordNotMatch.maybe(password == null);
        var user = getUserByUsername(username);
        PlugsExceptions.UserNotFound.maybe(user == null);
        assert user != null;
        PlugsExceptions.PasswordNotMatch.maybe(!matchUser(user, password.getBytes(StandardCharsets.UTF_8)));
        return user;
    }

    /**
     * 匹配用户密码是否正确, {@link UserBean} 参数必须是从数据库里查询出来的
     * */
    public boolean matchUser(UserBean user, byte[] password)
    {
        if(config.getPasswordSalt())
        {
            var saltReal = user.getPasswordSalt();
            var signatureReal = user.getPassword();

            var buffer = bufferMixture(saltReal, password);
            var signature = Encrypts.encodeHMAC(buffer, HMAC_MAC);

            return Arrays.equals(signature, signatureReal);
        }
        else
        {
            return Arrays.equals(password, user.getPassword());
        }
    }
    public boolean matchUser(UserBean user, String password)
    {
        return matchUser(user, password.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 对一个用户信息做处理, {@link UserBean#password} 字段必须储存的是明文密码
     * */
    @SuppressWarnings("JavadocReference")
    public void processUserPassword(UserBean user, String password)
    {
        if(config.getPasswordSalt())
        {
            var salt = UUID.randomUUID().toString().substring(0, 24).getBytes(StandardCharsets.US_ASCII);

            var buffer = bufferMixture(salt, password.getBytes(StandardCharsets.UTF_8));
            var signature = Encrypts.encodeHMAC(buffer, HMAC_MAC);

            user.setPassword(signature);
            user.setPasswordSalt(salt);
        }
        else
        {
            user.setPassword(password.getBytes(StandardCharsets.UTF_8));
            user.setPasswordSalt(null);
        }
    }

    /**
     * 将密码和盐混合到一起, 获得一个 byte[] 缓冲区
     * */
    public static byte[] bufferMixture(byte[] salt, byte[] password)
    {
        var ret = new byte[salt.length + password.length];
        System.arraycopy(salt, 0, ret, 0, salt.length);
        System.arraycopy(password, 0, ret, salt.length, password.length);
        return ret;
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
        user.setNickname(nickname);
        user.setTokenCookie(cookieToken);
        processUserPassword(user, password);

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
           // todo check timeLast range

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
     * @param tokenValue token 值
     * */
    public UserBean checkCookieToken(String tokenValue)
    {
        return checkCookieToken(tokenValue, System.currentTimeMillis());
    }
    /**
     * 手动指定时间戳并鉴权
     * @param timestamp 要对比的时间戳
     * */
    public UserBean checkCookieToken(String tokenValue, long timestamp)
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
            PlugsExceptions.TokenExpired.maybe(Long.parseLong(timeEnd) < timestamp);
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
