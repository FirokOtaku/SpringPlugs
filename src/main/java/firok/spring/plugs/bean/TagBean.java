package firok.spring.plugs.bean;

import lombok.Data;
import org.intellij.lang.annotations.Language;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = TagBean.TableName)
public class TagBean extends AbstractPlugBean implements Serializable
{
    @Serial
    private static final long serialVersionUID = Base + 30000 + 1;

    public static final String TableName = "d_plugs_tag";
    @SuppressWarnings("SqlNoDataSourceInspection")
    @Language("SQL")
    public static final String CreateTableMysql = """
            create table if not exists d_plugs_tag (
              id varchar(48) not null,
              primary key (id),
              timestamp_create bigint not null,
              timestamp_update bigint not null,
              
              tag_type varchar(32),
              target_id varchar(48),
              tag_value varchar(64)
            )
            """;

    /**
     * 标签类型
     * */
    String tagType;

    /**
     * 目标 id
     * */
    String targetId;

    /**
     * 标签值
     * */
    String tagValue;
}
