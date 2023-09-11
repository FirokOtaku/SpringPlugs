package firok.spring.plugs.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import firok.spring.plugs.bean.property.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = false)
public class StringIdTimestampLongBean
    implements IdProperty<String>,
        TimestampCreateLongProperty, TimestampUpdateLongProperty, TimestampDeleteLongProperty, IsDeleteProperty
{
    @TableId
    String id;
    Long timestampCreate, timestampUpdate, timestampDelete;
    Boolean isDelete;
}
