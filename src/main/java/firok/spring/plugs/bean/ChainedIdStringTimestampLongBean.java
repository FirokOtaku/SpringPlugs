package firok.spring.plugs.bean;

import firok.spring.plugs.bean.property.*;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;

@Data
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
