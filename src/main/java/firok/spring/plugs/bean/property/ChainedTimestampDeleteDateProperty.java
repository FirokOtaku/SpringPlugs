package firok.spring.plugs.bean.property;

import java.util.Date;

public interface ChainedTimestampDeleteDateProperty<TypeSelf extends ChainedTimestampDeleteDateProperty<?>>
{
    Date getTimestampDelete();

    TypeSelf setTimestampDelete(Date timestampDelete);
}
