package firok.spring.plugs.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import firok.spring.mvci.MVCIntrospective;
import lombok.Data;
import lombok.experimental.Accessors;
import org.intellij.lang.annotations.Language;

/**
 * 上传的文件数据
 * */
@Data
@MVCIntrospective
@TableName(FileBean.TableName)
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

    @TableField(exist = false)
    transient Long timestampDelete;

    @TableField(exist = false)
    transient Boolean isDelete;

}
