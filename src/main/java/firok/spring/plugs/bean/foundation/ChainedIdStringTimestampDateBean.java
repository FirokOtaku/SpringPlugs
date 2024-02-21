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
public class ChainedIdStringTimestampDateBean
    implements ChainedIdProperty<String, ChainedIdStringTimestampDateBean>,
        ChainedTimestampCreateDateProperty<ChainedIdStringTimestampDateBean>,
        ChainedTimestampUpdateDateProperty<ChainedIdStringTimestampDateBean>,
        ChainedTimestampDeleteDateProperty<ChainedIdStringTimestampDateBean>,
        ChainedIsDeleteProperty<ChainedIdStringTimestampDateBean>
{
    @Id
    String id;
    Date timestampCreate, timestampUpdate, timestampDelete;
    Boolean isDelete;
}
