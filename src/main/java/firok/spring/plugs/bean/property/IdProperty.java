package firok.spring.plugs.bean.property;

/**
 * @implNote 没有多继承, 只能这样了
 * */
public interface IdProperty<TypeId>
{
    TypeId getId();

    void setId(TypeId id);
}
