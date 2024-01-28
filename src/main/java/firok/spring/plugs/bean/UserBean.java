package firok.spring.plugs.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import firok.spring.mvci.Constants;
import firok.spring.mvci.MVCIntrospective;
import firok.spring.mvci.Param;
import firok.spring.plugs.mvci.GeneralServiceImpl;
import lombok.Data;
import lombok.experimental.Accessors;
import org.intellij.lang.annotations.Language;

/**
 * 用户数据
 * */
@Data
@MVCIntrospective
@TableName(UserBean.TableName)
@Accessors(chain = true)
public class UserBean extends ChainedIdStringTimestampLongBean
{
    public static final String TableName = "d_plugs_user";
    @SuppressWarnings("SqlNoDataSourceInspection")
    @Language("SQL")
    public static final String CreateTableMysql = """
            create table if not exists d_plugs_user (
              id varchar(48) not null,
              primary key (id),
              timestamp_create bigint not null,
              timestamp_update bigint not null,
              
              username varchar(64) not null,
              nickname varchar(64) not null,
              password varchar(64) not null,
              token_cookie varchar(64) not null
            )
            """;

    /**
     * 用户名
     * */
    String username;
    /**
     * 昵称
     * */
    String nickname;
    /**
     * 密码
     * */
    String password;
    /**
     * 用户 token 加盐
     * */
    String tokenCookie;

    @TableField(exist = false)
    transient Long timestampDelete;

    @TableField(exist = false)
    transient Boolean isDelete;
}
