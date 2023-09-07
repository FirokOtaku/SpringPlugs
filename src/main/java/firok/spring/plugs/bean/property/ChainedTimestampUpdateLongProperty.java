package firok.spring.plugs.bean.property;

public interface ChainedTimestampUpdateLongProperty<TypeSelf extends ChainedTimestampUpdateLongProperty<?>>
{
    Long getTimestampUpdate();

    TypeSelf setTimestampUpdate(Long timestampUpdate);
}
