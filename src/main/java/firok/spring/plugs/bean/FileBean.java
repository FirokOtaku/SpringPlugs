package firok.spring.plugs.bean;

import lombok.Data;
import org.intellij.lang.annotations.Language;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

/**
 * 上传的文件数据
 * */
@Data
@Entity
@Table(name = FileBean.TableName)
public class FileBean extends AbstractPlugBean implements Serializable
{
    @Serial
    private static final long serialVersionUID = Base + 20000 + 1;

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
}
