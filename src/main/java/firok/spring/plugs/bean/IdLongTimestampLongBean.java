package firok.spring.plugs.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import firok.spring.plugs.bean.property.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = false)
public class IdLongTimestampLongBean
    implements IdProperty<Long>,
        TimestampCreateLongProperty, TimestampUpdateLongProperty, TimestampDeleteLongProperty, IsDeleteProperty
{
    @TableId
    Long id;
    Long timestampCreate, timestampUpdate, timestampDelete;
    Boolean isDelete;
}
