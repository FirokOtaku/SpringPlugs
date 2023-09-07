package firok.spring.plugs.bean.property;

import java.util.Date;

public interface ChainedTimestampUpdateDateProperty<TypeSelf extends ChainedTimestampUpdateDateProperty<?>>
{
    Date getTimestampUpdate();

    TypeSelf setTimestampUpdate(Date timestampUpdate);
}
