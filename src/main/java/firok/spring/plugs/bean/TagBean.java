package firok.spring.plugs.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import firok.spring.mvci.MVCIntrospective;
import lombok.Data;
import lombok.experimental.Accessors;
import org.intellij.lang.annotations.Language;

@Data
@MVCIntrospective
@TableName(TagBean.TableName)
@Accessors(chain = true)
public class TagBean extends ChainedIdStringTimestampLongBean
{
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
