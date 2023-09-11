package firok.spring.plugs.mvci;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import firok.spring.plugs.bean.property.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class GeneralServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T>
{
    public boolean generalSave(T en)
    {
        return generalSave(en, new Date());
    }
    public boolean generalSave(T en, Date timestamp)
    {
        if(behaviorCreateSetTimestampCreate)
            updateTimestampCreate(en, timestamp);
        if(behaviorCreateSetTimestampUpdate)
            updateTimestampUpdate(en, timestamp);
        return super.save(en);
    }

    public boolean generalSaveBatch(Collection<T> ens, int batchSize)
    {
        return generalSaveBatch(ens, batchSize, new Date());
    }

    public boolean generalSaveBatch(Collection<T> ens, int batchSize, Date timestamp)
    {
        for(var en : ens)
        {
            if(behaviorCreateSetTimestampCreate)
                updateTimestampCreate(en, timestamp);
            if(behaviorCreateSetTimestampUpdate)
                updateTimestampUpdate(en, timestamp);
        }
        return super.saveBatch(ens, batchSize);
    }

    public boolean generalUpdateById(T en)
    {
        return generalUpdateById(en, new Date());
    }

    public boolean generalUpdateById(T en, Date timestamp)
    {
        if(behaviorUpdateSetTimestampUpdate)
            updateTimestampUpdate(en, timestamp);
        return super.updateById(en);
    }

    public boolean updateBatchById(Collection<T> ens, int batchSize)
    {
        return updateBatchById(ens, batchSize, new Date());
    }

    public boolean updateBatchById(Collection<T> ens, int batchSize, Date timestamp)
    {
        if(behaviorUpdateSetTimestampUpdate)
        {
            for(var en : ens)
            {
                updateTimestampUpdate(en, timestamp);
            }
        }
        return super.updateBatchById(ens, batchSize);
    }

    public boolean generalUpdate(T en, Wrapper<T> wrapper)
    {
        return generalUpdate(en, wrapper, new Date());
    }

    public boolean generalUpdate(T en, Wrapper<T> wrapper, Date timestamp)
    {
        if(behaviorUpdateSetTimestampUpdate)
            updateTimestampUpdate(en, timestamp);
        return super.update(en, wrapper);
    }

    public boolean generalDeleteById(T en)
    {
        return generalDeleteById(en, new Date());
    }

    public boolean generalDeleteById(T en, Date timestamp)
    {
        if(behaviorDeleteLogical)
        {
            if(behaviorDeleteSetTimestampUpdate)
                updateTimestampUpdate(en, timestamp);
            if(behaviorDeleteSetTimestampDelete)
                updateTimestampDelete(en, timestamp);
            if(behaviorDeleteSetIsDelete)
                updateIsDelete(en, true);

            return super.updateById(en);
        }
        else
        {
            return super.removeById(en);
        }
    }

    private static void updateTimestampCreate(Object en, Date timestampCreate)
    {
        if(en instanceof TimestampCreateDateProperty p)
            p.setTimestampCreate(timestampCreate);
        if(en instanceof ChainedTimestampCreateDateProperty<?> p)
            p.setTimestampCreate(timestampCreate);
        if(en instanceof TimestampCreateLongProperty p)
            p.setTimestampCreate(timestampCreate.getTime());
        if(en instanceof ChainedTimestampCreateLongProperty<?> p)
            p.setTimestampCreate(timestampCreate.getTime());
    }

    private static void updateTimestampUpdate(Object en, Date timestampUpdate)
    {
        if(en instanceof TimestampUpdateDateProperty p)
            p.setTimestampUpdate(timestampUpdate);
        if(en instanceof ChainedTimestampUpdateDateProperty<?> p)
            p.setTimestampUpdate(timestampUpdate);
        if(en instanceof TimestampUpdateLongProperty p)
            p.setTimestampUpdate(timestampUpdate.getTime());
        if(en instanceof ChainedTimestampUpdateLongProperty<?> p)
            p.setTimestampUpdate(timestampUpdate.getTime());
    }

    private static void updateTimestampDelete(Object en, Date timestampDelete)
    {
        if(en instanceof TimestampDeleteDateProperty p)
            p.setTimestampDelete(timestampDelete);
        if(en instanceof ChainedTimestampDeleteDateProperty<?> p)
            p.setTimestampDelete(timestampDelete);
        if(en instanceof TimestampDeleteLongProperty p)
            p.setTimestampDelete(timestampDelete.getTime());
        if(en instanceof ChainedTimestampDeleteLongProperty<?> p)
            p.setTimestampDelete(timestampDelete.getTime());
    }

    private static void updateIsDelete(Object en, Boolean isDelete)
    {
        if(en instanceof IsDeleteProperty p)
            p.setIsDelete(isDelete);
        if(en instanceof ChainedIsDeleteProperty<?> p)
            p.setIsDelete(isDelete);
    }

    protected boolean behaviorCreateSetTimestampCreate = true;
    protected boolean behaviorCreateSetTimestampUpdate = true;
    protected boolean behaviorUpdateSetTimestampUpdate = true;
    protected boolean behaviorDeleteSetTimestampUpdate = true;
    protected boolean behaviorDeleteSetTimestampDelete = true;
    protected boolean behaviorDeleteSetIsDelete = true;
    protected boolean behaviorDeleteLogical = true;

    /**
     * 自动模板.
     * 需为 @MVCIntrospective.extraParams 提供如下参数:
     * <ul>
     *     <li>##CREATE_SET_TIMESTAMP_CREATE## - (布尔值) 是否在插入实体时更新 timestampCreate 字段</li>
     *     <li>##CREATE_SET_TIMESTAMP_UPDATE## - (布尔值) 是否在插入实体时更新 timestampUpdate 字段</li>
     *     <li>##UPDATE_SET_TIMESTAMP_UPDATE## - (布尔值) 是否在修改实体时更新 timestampUpdate 字段</li>
     *     <li>##DELETE_SET_TIMESTAMP_UPDATE## - (布尔值) 是否在删除实体时更新 timestampUpdate 字段</li>
     *     <li>##DELETE_SET_TIMESTAMP_DELETE## - (布尔值) 是否在删除实体时更新 timestampDelete 字段</li>
     *     <li>##DELETE_SET_IS_DELETE## - (布尔值) 是否在删除实体时更新 isDelete 字段</li>
     *     <li>##DELETE_LOGICAL## - (布尔值) 是否逻辑删除</li>
     * </ul>
     *
     * @see firok.spring.mvci.MVCIntrospective
     * @see com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
     * */
    public static final String Template = """
package ##SERVICE_IMPL_PACKAGE##;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ##MAPPER_PACKAGE##.##MAPPER_NAME##;
import ##SERVICE_PACKAGE##.##SERVICE_NAME##;
import ##BEAN_PACKAGE##.##BEAN_NAME_FULL##;
import org.springframework.stereotype.Service;

import firok.spring.plugs.mvci.GeneralServiceImpl;

@Service
public class ##SERVICE_IMPL_NAME##
extends GeneralServiceImpl<##MAPPER_NAME##, ##BEAN_NAME_FULL##> implements ##SERVICE_NAME##
{
    {
        super.behaviorCreateSetTimestampCreate = ##CREATE_SET_TIMESTAMP_CREATE##;
        super.behaviorCreateSetTimestampUpdate = ##CREATE_SET_TIMESTAMP_UPDATE##;
        super.behaviorUpdateSetTimestampUpdate = ##UPDATE_SET_TIMESTAMP_UPDATE##;
        super.behaviorDeleteSetTimestampUpdate = ##DELETE_SET_TIMESTAMP_UPDATE##;
        super.behaviorDeleteSetTimestampDelete = ##DELETE_SET_TIMESTAMP_DELETE##;
        super.behaviorDeleteSetIsDelete = ##DELETE_SET_IS_DELETE##;
        super.behaviorDeleteLogical = ##DELETE_LOGICAL##;
    }
}
""";
}
