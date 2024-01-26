package firok.spring.plugs.service_compact;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import firok.spring.plugs.bean.TagBean;
import firok.spring.plugs.config.FileConfig;
import firok.spring.plugs.mapper.TagMapper;
import firok.spring.plugs.mvci.GeneralServiceImpl;
import firok.spring.plugs.util.TableUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.*;

import static firok.topaz.general.Collections.isEmpty;

@Service
@ConditionalOnBean(FileConfig.class)
public class CompactTagService extends AbstractCompactService
{
    @Autowired
    GeneralServiceImpl<TagMapper, TagBean> service;

    @Override
    protected final String sqlTableName()
    {
        return TagBean.TableName;
    }

    @Override
    protected String sqlShowTables()
    {
        return TableUtil.SqlShowTablesMysql;
    }

    @Override
    protected String sqlCreateTable()
    {
        return TagBean.CreateTableMysql;
    }

    public List<String> getTargetTags(
            @NotNull String tagType,
            @NotNull String targetId
    )
    {
        var qw = new QueryWrapper<TagBean>().lambda()
                .eq(TagBean::getTagType, tagType)
                .eq(TagBean::getTargetId, targetId);
        return service.list(qw).stream()
                      .map(TagBean::getTagValue)
                      .toList();
    }

    public List<String> getTargetTags(
            @NotNull Enum<?> tagType,
            @NotNull String targetId
    )
    {
        return getTargetTags(tagType.name(), targetId);
    }

    public void setTargetTags(
            @NotNull String tagType,
            @NotNull String targetId,
            @Nullable Collection<String> tagValues
    )
    {
        var qw = new QueryWrapper<TagBean>().lambda()
                .eq(TagBean::getTagType, tagType)
                .eq(TagBean::getTargetId, targetId);
        service.remove(qw);
        if(isEmpty(tagValues)) return;
        var now = new Date();
        var tags = new ArrayList<TagBean>();
        for(var tagValue : tagValues)
        {
            var tag = new TagBean();
            tag.setId(UUID.randomUUID().toString());
            tag.setTagType(tagType);
            tag.setTargetId(targetId);
            tag.setTagValue(tagValue);
            tags.add(tag);
        }
        service.generalSaveBatch(tags, 50, now);
    }

    public void setTargetTags(
            @NotNull Enum<?> tagType,
            @NotNull String targetId,
            @Nullable Collection<String> tagValues
    )
    {
        setTargetTags(tagType.name(), targetId, tagValues);
    }

    public void setTargetTags(
            @NotNull String tagType,
            @NotNull String targetId,
            @Nullable String... tagValues
    )
    {
        setTargetTags(
                tagType,
                targetId,
                isEmpty(tagValues) ? Collections.emptyList() : Arrays.asList(tagValues)
        );
    }

    public void setTargetTags(
            @NotNull Enum<?> tagType,
            @NotNull String targetId,
            @Nullable String... tagValues
    )
    {
        setTargetTags(
                tagType,
                targetId,
                isEmpty(tagValues) ? Collections.emptyList() : Arrays.asList(tagValues)
        );
    }
}
