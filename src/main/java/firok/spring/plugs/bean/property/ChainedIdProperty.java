package firok.spring.plugs.bean.property;

import java.io.Serializable;

/**
 * @implNote 没有多继承, 只能这样了
 * */
public interface ChainedIdProperty<TypeId extends Serializable, TypeSelf extends ChainedIdProperty<TypeId, ?>>
{
    TypeId getId();

    TypeSelf setId(TypeId id);
}
