package firok.spring.plugs.bean.property;

import java.util.Date;

public interface ChainedTimestampCreateDateProperty<TypeSelf extends ChainedTimestampCreateDateProperty<?>>
{
    Date getTimestampCreate();

    TypeSelf setTimestampCreate(Date timestampCreate);
}
