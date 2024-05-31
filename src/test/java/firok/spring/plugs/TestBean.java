package firok.spring.plugs;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "d_ebean_pagination")
public class TestBean
{
    @Id
    int id;
}
