package firok.spring.plugs.bean.foundation;

import firok.spring.plugs.bean.property.*;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
@Accessors(chain = false)
public class IdLongTimestampDateBean
    implements IdProperty<Long>,
        TimestampCreateDateProperty, TimestampUpdateDateProperty, TimestampDeleteDateProperty, IsDeleteProperty
{
    @Id
    Long id;
    Date timestampCreate, timestampUpdate, timestampDelete;
    Boolean isDelete;
}
