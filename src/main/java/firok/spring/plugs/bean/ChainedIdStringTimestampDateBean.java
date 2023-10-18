package firok.spring.plugs.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import firok.spring.plugs.bean.property.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ChainedIdStringTimestampDateBean
    implements ChainedIdProperty<String, ChainedIdStringTimestampDateBean>,
        ChainedTimestampCreateDateProperty<ChainedIdStringTimestampDateBean>,
        ChainedTimestampUpdateDateProperty<ChainedIdStringTimestampDateBean>,
        ChainedTimestampDeleteDateProperty<ChainedIdStringTimestampDateBean>,
        ChainedIsDeleteProperty<ChainedIdStringTimestampDateBean>
{
    @TableId
    String id;
    Date timestampCreate, timestampUpdate, timestampDelete;
    Boolean isDelete;
}
