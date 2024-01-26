package firok.spring.plugs.service_compact;

import firok.spring.plugs.bean.UserBean;
import firok.spring.plugs.util.TableUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 会自动检查表结构是否存在的服务.
 * 服务启动后如果发现表结构不存在则会尝试创建.
 * */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public abstract class AbstractCompactService
{
    @Autowired
    private DataSource ds;

    /**
     * 显示所有表所用的 SQL 语句
     * @see TableUtil#SqlShowTablesMysql
     * */
    protected abstract String sqlShowTables();

    /**
     * 创建表结构所需的 SQL 语句
     * @see UserBean#CreateTableMysql
     * */
    protected abstract String sqlCreateTable();

    /**
     * 当前服务基于什么表名
     * */
    protected abstract String sqlTableName();

    @PostConstruct
    private void postConstruct() throws SQLException
    {
        TableUtil.createTableIfNotExist(
                ds,
                this.sqlTableName(),
                this.sqlShowTables(),
                this.sqlCreateTable()
        );
    }
}
