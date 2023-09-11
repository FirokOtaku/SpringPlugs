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
    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize)
    {
        return super.saveBatch(entityList, batchSize);
    }

    @Override
    public boolean saveOrUpdate(T entity)
    {
        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize)
    {
        return super.saveOrUpdateBatch(entityList, batchSize);
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize)
    {
        return super.updateBatchById(entityList, batchSize);
    }

    @Override
    public boolean removeById(Serializable id)
    {
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<?> list)
    {
        return super.removeByIds(list);
    }

    @Override
    public boolean removeById(Serializable id, boolean useFill)
    {
        return super.removeById(id, useFill);
    }

    @Override
    public boolean removeBatchByIds(Collection<?> list, int batchSize)
    {
        return super.removeBatchByIds(list, batchSize);
    }

    @Override
    public boolean removeBatchByIds(Collection<?> list, int batchSize, boolean useFill)
    {
        return super.removeBatchByIds(list, batchSize, useFill);
    }

    @Override
    public boolean save(T entity)
    {
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<T> entityList)
    {
        return super.saveBatch(entityList);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList)
    {
        return super.saveOrUpdateBatch(entityList);
    }

    @Override
    public boolean removeById(T entity)
    {
        return super.removeById(entity);
    }

    @Override
    public boolean removeByMap(Map<String, Object> columnMap)
    {
        return super.removeByMap(columnMap);
    }

    @Override
    public boolean remove(Wrapper<T> queryWrapper)
    {
        return super.remove(queryWrapper);
    }

    @Override
    public boolean removeByIds(Collection<?> list, boolean useFill)
    {
        return super.removeByIds(list, useFill);
    }

    @Override
    public boolean removeBatchByIds(Collection<?> list)
    {
        return super.removeBatchByIds(list);
    }

    @Override
    public boolean removeBatchByIds(Collection<?> list, boolean useFill)
    {
        return super.removeBatchByIds(list, useFill);
    }

    @Override
    public boolean updateById(T entity)
    {
        return super.updateById(entity);
    }

    @Override
    public boolean update(Wrapper<T> updateWrapper)
    {
        return super.update(updateWrapper);
    }

    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper)
    {
        return super.update(entity, updateWrapper);
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList)
    {
        return super.updateBatchById(entityList);
    }

    @Override
    public T getById(Serializable id)
    {
        return super.getById(id);
    }

    @Override
    public boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper)
    {
        return super.saveOrUpdate(entity, updateWrapper);
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
    }
}
""";
}
