package firok.spring.plugs.bean.foundation;

import firok.spring.plugs.bean.property.*;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@Accessors(chain = false)
public class StringIdTimestampLongBean
    implements IdProperty<String>,
        TimestampCreateLongProperty, TimestampUpdateLongProperty, TimestampDeleteLongProperty, IsDeleteProperty
{
    @Id
    String id;
    Long timestampCreate, timestampUpdate, timestampDelete;
    Boolean isDelete;
}
