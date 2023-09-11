package firok.spring.plugs.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import firok.spring.plugs.bean.property.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = false)
public class StringIdTimestampDateBean
    implements IdProperty<String>,
        TimestampCreateDateProperty, TimestampUpdateDateProperty, TimestampDeleteDateProperty, IsDeleteProperty
{
    @TableId
    String id;
    Date timestampCreate, timestampUpdate, timestampDelete;
    Boolean isDelete;
}
