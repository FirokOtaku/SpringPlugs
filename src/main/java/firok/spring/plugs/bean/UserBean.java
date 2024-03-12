package firok.spring.plugs.bean;

import lombok.Data;
import org.intellij.lang.annotations.Language;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户数据
 * */
@Data
@Entity
@Table(name = UserBean.TableName)
public class UserBean extends AbstractPlugBean
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
              password_salt varchar(64),
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
     * 密码盐值
     * */
    String passwordSalt;
    /**
     * 用户 token 加盐
     * */
    String tokenCookie;
}
