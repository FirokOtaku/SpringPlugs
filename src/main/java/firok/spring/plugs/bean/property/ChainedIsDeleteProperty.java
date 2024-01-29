package firok.spring.plugs.bean.property;

public interface ChainedIsDeleteProperty<TypeSelf extends ChainedIsDeleteProperty<?>>
{
    Boolean getIsDelete();

    TypeSelf setIsDelete(Boolean isDelete);
}
