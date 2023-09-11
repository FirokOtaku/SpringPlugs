package firok.spring.plugs.bean.property;

import java.util.Date;

public interface ChainedIsDeleteProperty<TypeSelf extends ChainedIsDeleteProperty<?>>
{
    Boolean getIsDelete();

    TypeSelf setIsDelete(Boolean isDelete);
}
