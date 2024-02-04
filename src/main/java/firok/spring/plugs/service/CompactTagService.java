package firok.spring.plugs.service;

import firok.spring.plugs.bean.TagBean;
import firok.spring.plugs.bean.query.QTagBean;
import firok.spring.plugs.config.TagConfig;
import firok.spring.plugs.util.TableUtil;
import io.ebean.DB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.*;

import static firok.topaz.general.Collections.isEmpty;

@Service
@ConditionalOnBean(TagConfig.class)
public class CompactTagService extends AbstractCompactService
{
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

    @Override
    protected boolean shouldAutoCreateTable()
    {
        return config.getAutoCreateTable();
    }

    @Autowired
    TagConfig config;

    public List<String> getTargetTags(
            @NotNull String tagType,
            @NotNull String targetId
    )
    {
        return new QTagBean()
                .tagType.eq(tagType)
                .targetId.eq(targetId)
                .findList()
                .stream()
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
        new QTagBean()
                .tagType.eq(tagType)
                .targetId.eq(targetId)
                .delete();
        if(isEmpty(tagValues)) return;
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
        DB.saveAll(tags);
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
