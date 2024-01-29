package firok.spring.plugs.bean;

import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@Accessors(chain = true)
public abstract class AbstractPlugBean
{
    @Id
    String id;

    @WhenCreated
    Long timestampCreate;

    @WhenModified
    Long timestampUpdate;
}
