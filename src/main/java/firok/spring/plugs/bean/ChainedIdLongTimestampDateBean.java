package firok.spring.plugs.bean;

import firok.spring.plugs.bean.property.*;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.util.Date;

@Data
@Accessors(chain = true)
public class ChainedIdLongTimestampDateBean
    implements ChainedIdProperty<Long, ChainedIdLongTimestampDateBean>,
        ChainedTimestampCreateDateProperty<ChainedIdLongTimestampDateBean>,
        ChainedTimestampUpdateDateProperty<ChainedIdLongTimestampDateBean>,
        ChainedTimestampDeleteDateProperty<ChainedIdLongTimestampDateBean>,
        ChainedIsDeleteProperty<ChainedIdLongTimestampDateBean>
{
    @Id
    Long id;
    Date timestampCreate, timestampUpdate, timestampDelete;
    Boolean isDelete;
}
