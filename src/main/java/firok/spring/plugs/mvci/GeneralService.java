package firok.spring.plugs.mvci;

import firok.spring.mvci.MVCIntrospective;
import firok.spring.plugs.bean.property.*;
import firok.topaz.general.Collections;
import io.ebean.DB;
import io.ebean.Query;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static firok.topaz.general.Collections.sizeOf;

public class GeneralService<TypeBean>
{
    protected final Class<TypeBean> classBean;
    public GeneralService(Class<TypeBean> classBean)
    {
        this.classBean = classBean;
    }

    /**
     * @since 0.7.0-j21
     * */
    public <TypeKey, TypeValue> Map<TypeKey, TypeValue> queryMappingKeyValue(
            Query<TypeBean> qw,
            Function<TypeBean, TypeKey> functionKey,
            Function<TypeBean, TypeValue> functionValue
    )
    {
        return Collections.mappingKeyValue(
                qw.findList(),
                functionKey,
                functionValue
        );
    }

    /**
     * @since 0.7.0-j21
     * */
    public <TypeKey, TypeValue> Map<TypeKey, TypeValue> queryMappingKeyValue(
            Function<TypeBean, TypeKey> functionKey,
            Function<TypeBean, TypeValue> functionValue
    )
    {
        return Collections.mappingKeyValue(
                DB.createQuery(classBean).findList(),
                functionKey,
                functionValue
        );
    }

    /**
     * @since 0.7.0-j21
     * */
    public <TypeKey> Map<TypeKey, TypeBean> queryMappingKeyEntity(
            Query<TypeBean> qw,
            Function<TypeBean, TypeKey> functionKey
    )
    {
        return Collections.mappingKeyEntity(
                qw.findList(),
                functionKey
        );
    }

    /**
     * @since 0.7.0-j21
     * */
    public <TypeKey> Map<TypeKey, TypeBean> queryMappingKeyEntity(
            Function<TypeBean, TypeKey> functionKey
    )
    {
        return Collections.mappingKeyEntity(
                DB.createQuery(classBean).findList(),
                functionKey
        );
    }

    // todo high 添加更多 firok.topaz.general.Collections 的方法

    public boolean generalSave(TypeBean en)
    {
        return generalSave(en, new Date());
    }
    public boolean generalSave(TypeBean en, Date timestamp)
    {
        try
        {
            if(behaviorCreateSetTimestampCreate)
                updateTimestampCreate(en, timestamp);
            if(behaviorCreateSetTimestampUpdate)
                updateTimestampUpdate(en, timestamp);
            DB.save(en);
            return true;
        }
        catch (Exception any)
        {
            return false;
        }
    }

    /**
     * @since 0.7.0-j21
     * */
    public boolean generalSaveBatch(Collection<TypeBean> ens)
    {
        return generalSaveBatch(ens, new Date());
    }

    public boolean generalSaveBatch(Collection<TypeBean> ens, Date timestamp)
    {
        for(var en : ens)
        {
            if(behaviorCreateSetTimestampCreate)
                updateTimestampCreate(en, timestamp);
            if(behaviorCreateSetTimestampUpdate)
                updateTimestampUpdate(en, timestamp);
        }
        return sizeOf(ens) == DB.saveAll(ens);
    }

    public boolean generalUpdateById(TypeBean en)
    {
        return generalUpdateById(en, new Date());
    }

    public boolean generalUpdateById(TypeBean en, Date timestamp)
    {
        try
        {
            if(behaviorUpdateSetTimestampUpdate)
                updateTimestampUpdate(en, timestamp);
            DB.update(en);
            return true;
        }
        catch (Exception any)
        {
            return handleException(any);
        }
    }

    public boolean updateBatchById(Collection<TypeBean> ens, Date timestamp)
    {
        try
        {
            if(behaviorUpdateSetTimestampUpdate)
            {
                for(var en : ens)
                {
                    updateTimestampUpdate(en, timestamp);
                }
            }
            DB.updateAll(ens);
            return true;
        }
        catch (Exception any)
        {
            return handleException(any);
        }
    }

    public boolean generalDeleteById(TypeBean en)
    {
        return generalDeleteById(en, new Date());
    }

    public boolean generalDeleteById(TypeBean en, Date timestamp)
    {
        try
        {
            if(behaviorDeleteLogical)
            {
                if(behaviorDeleteSetTimestampUpdate)
                    updateTimestampUpdate(en, timestamp);
                if(behaviorDeleteSetTimestampDelete)
                    updateTimestampDelete(en, timestamp);
                if(behaviorDeleteSetIsDelete)
                    updateIsDelete(en, true);

                DB.update(en);
                return true;
            }
            else
            {
                return DB.delete(en);
            }
        }
        catch (Exception any)
        {
            return handleException(any);
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
    protected boolean behaviorThrowException = false;

    private boolean handleException(Exception any)
    {
        if(behaviorThrowException)
        {
            throw new RuntimeException(any);
        }
        return false;
    }

    public static final String TemplateService = """
            package ##SERVICE_PACKAGE##;
                        
            public interface ##SERVICE_NAME## { }
            """;

    /**
     * 自动模板.
     * 需为 {@link MVCIntrospective#extraParams} 提供如下参数:
     * <ul>
     *     <li>##CREATE_SET_TIMESTAMP_CREATE## - (布尔值) 是否在插入实体时更新 timestampCreate 字段</li>
     *     <li>##CREATE_SET_TIMESTAMP_UPDATE## - (布尔值) 是否在插入实体时更新 timestampUpdate 字段</li>
     *     <li>##UPDATE_SET_TIMESTAMP_UPDATE## - (布尔值) 是否在修改实体时更新 timestampUpdate 字段</li>
     *     <li>##DELETE_SET_TIMESTAMP_UPDATE## - (布尔值) 是否在删除实体时更新 timestampUpdate 字段</li>
     *     <li>##DELETE_SET_TIMESTAMP_DELETE## - (布尔值) 是否在删除实体时更新 timestampDelete 字段</li>
     *     <li>##DELETE_SET_IS_DELETE## - (布尔值) 是否在删除实体时更新 isDelete 字段</li>
     *     <li>##DELETE_LOGICAL## - (布尔值) 是否逻辑删除</li>
     *     <li>##THROW_EXCEPTION## - (布尔值) 是否抛出内部异常</li>
     * </ul>
     *
     * @see firok.spring.mvci.MVCIntrospective
     * */
    public static final String TemplateServiceImpl = """
package ##SERVICE_IMPL_PACKAGE##;

import ##BEAN_PACKAGE##.##BEAN_NAME_FULL##;
import org.springframework.stereotype.Service;

import firok.spring.plugs.mvci.GeneralService;

@Service
public class ##SERVICE_IMPL_NAME##
extends GeneralService<##BEAN_NAME_FULL##>
{
    public ##SERVICE_IMPL_NAME##()
    {
        super(##BEAN_NAME_FULL##.class);
        super.behaviorCreateSetTimestampCreate = ##CREATE_SET_TIMESTAMP_CREATE##;
        super.behaviorCreateSetTimestampUpdate = ##CREATE_SET_TIMESTAMP_UPDATE##;
        super.behaviorUpdateSetTimestampUpdate = ##UPDATE_SET_TIMESTAMP_UPDATE##;
        super.behaviorDeleteSetTimestampUpdate = ##DELETE_SET_TIMESTAMP_UPDATE##;
        super.behaviorDeleteSetTimestampDelete = ##DELETE_SET_TIMESTAMP_DELETE##;
        super.behaviorDeleteSetIsDelete = ##DELETE_SET_IS_DELETE##;
        super.behaviorDeleteLogical = ##DELETE_LOGICAL##;
        super.behaviorThrowException = ##THROW_EXCEPTION##;
    }
}
""";
}
