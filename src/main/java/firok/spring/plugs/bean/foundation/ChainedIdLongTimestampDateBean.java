package firok.spring.plugs.bean.foundation;

import firok.spring.plugs.bean.property.*;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
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
