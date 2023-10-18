package firok.spring.plugs.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import firok.spring.plugs.bean.property.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ChainedIdStringTimestampLongBean
    implements ChainedIdProperty<String, ChainedIdStringTimestampLongBean>,
        ChainedTimestampCreateLongProperty<ChainedIdStringTimestampLongBean>,
        ChainedTimestampUpdateLongProperty<ChainedIdStringTimestampLongBean>,
        ChainedTimestampDeleteLongProperty<ChainedIdStringTimestampLongBean>,
        ChainedIsDeleteProperty<ChainedIdStringTimestampLongBean>
{
    @TableId
    String id;
    Long timestampCreate, timestampUpdate, timestampDelete;
    Boolean isDelete;
}
