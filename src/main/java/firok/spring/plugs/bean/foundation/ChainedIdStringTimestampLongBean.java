package firok.spring.plugs.bean.foundation;

import firok.spring.plugs.bean.property.*;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@Accessors(chain = true)
public class ChainedIdStringTimestampLongBean
    implements ChainedIdProperty<String, ChainedIdStringTimestampLongBean>,
        ChainedTimestampCreateLongProperty<ChainedIdStringTimestampLongBean>,
        ChainedTimestampUpdateLongProperty<ChainedIdStringTimestampLongBean>,
        ChainedTimestampDeleteLongProperty<ChainedIdStringTimestampLongBean>,
        ChainedIsDeleteProperty<ChainedIdStringTimestampLongBean>
{
    @Id
    String id;
    Long timestampCreate, timestampUpdate, timestampDelete;
    Boolean isDelete;
}
