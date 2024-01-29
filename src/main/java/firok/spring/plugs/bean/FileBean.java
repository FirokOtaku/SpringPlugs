package firok.spring.plugs.bean;

import firok.spring.mvci.MVCIntrospective;
import lombok.Data;
import lombok.experimental.Accessors;
import org.intellij.lang.annotations.Language;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 上传的文件数据
 * */
@Data
@Entity
@Table(name = FileBean.TableName)
@MVCIntrospective
@Accessors(chain = true)
public class FileBean extends ChainedIdStringTimestampLongBean
{
    public static final String TableName = "d_plugs_file";
    @SuppressWarnings("SqlNoDataSourceInspection")
    @Language("SQL")
    public static final String CreateTableMysql = """
            create table if not exists d_plugs_file (
              id varchar(48) not null,
              primary key (id),
              timestamp_create bigint not null,
              timestamp_update bigint not null,
              
              file_name varchar(256),
              file_size bigint not null
            )
            """;

    /**
     * 文件名
     * */
    String fileName;
    /**
     * 文件大小
     * */
    Long fileSize;

    @Transient
    transient Long timestampDelete;

    @Transient
    transient Boolean isDelete;
}
