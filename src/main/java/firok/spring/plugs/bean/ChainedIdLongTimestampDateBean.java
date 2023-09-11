package firok.spring.plugs.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import firok.spring.plugs.bean.property.*;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ChainedIdLongTimestampDateBean
    implements IdProperty<Long>,
        TimestampCreateDateProperty, TimestampUpdateDateProperty, TimestampDeleteDateProperty, IsDeleteProperty
{
    @TableId
    Long id;
    Date timestampCreate, timestampUpdate, timestampDelete;
    Boolean isDelete;
}
