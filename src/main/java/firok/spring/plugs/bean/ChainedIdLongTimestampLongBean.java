package firok.spring.plugs.bean;

import firok.spring.plugs.bean.property.ChainedIdProperty;
import firok.spring.plugs.bean.property.ChainedIsDeleteProperty;
import firok.spring.plugs.bean.property.ChainedTimestampCreateLongProperty;
import firok.spring.plugs.bean.property.ChainedTimestampDeleteLongProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@Accessors(chain = true)
public class ChainedIdLongTimestampLongBean
    implements ChainedIdProperty<Long, ChainedIdLongTimestampLongBean>,
        ChainedTimestampCreateLongProperty<ChainedIdLongTimestampLongBean>,
        ChainedTimestampDeleteLongProperty<ChainedIdLongTimestampLongBean>,
        ChainedIsDeleteProperty<ChainedIdLongTimestampLongBean>
{
    @Id
    Long id;
    Long timestampCreate, timestampUpdate, timestampDelete;
    Boolean isDelete;
}
