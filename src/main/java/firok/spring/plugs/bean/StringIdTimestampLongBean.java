package firok.spring.plugs.bean;

import firok.spring.plugs.bean.property.*;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;

@Data
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
