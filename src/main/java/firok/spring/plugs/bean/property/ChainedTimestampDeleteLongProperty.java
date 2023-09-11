package firok.spring.plugs.bean.property;

public interface ChainedTimestampDeleteLongProperty<TypeSelf extends ChainedTimestampDeleteLongProperty<?>>
{
    Long getTimestampDelete();

    TypeSelf setTimestampDelete(Long timestampDelete);
}
