package firok.spring.plugs.bean.property;

public interface ChainedTimestampCreateLongProperty<TypeSelf extends ChainedTimestampCreateLongProperty<?>>
{
    Long getTimestampCreate();

    TypeSelf setTimestampCreate(Long timestampCreate);
}
