package firok.spring.plugs.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import firok.spring.plugs.bean.property.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ChainedIdLongTimestampLongBean
    implements IdProperty<Long>,
        TimestampCreateLongProperty, TimestampUpdateLongProperty, TimestampDeleteLongProperty, IsDeleteProperty
{
    @TableId
    Long id;
    Long timestampCreate, timestampUpdate, timestampDelete;
    Boolean isDelete;
}
