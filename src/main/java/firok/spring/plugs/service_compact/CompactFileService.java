package firok.spring.plugs.service_compact;

import firok.spring.plugs.bean.FileBean;
import firok.spring.plugs.config.FileConfig;
import firok.spring.plugs.mvci.GeneralService;
import firok.spring.plugs.util.TableUtil;
import firok.topaz.hash.IHashMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@ConditionalOnBean(FileConfig.class)
public class CompactFileService extends AbstractCompactService
{
    @Autowired
    GeneralService<FileBean> service;

    @Override
    protected String sqlShowTables()
    {
        return TableUtil.SqlShowTablesMysql;
    }

    @Override
    protected String sqlCreateTable()
    {
        return FileBean.CreateTableMysql;
    }

    @Override
    protected String sqlTableName()
    {
        return FileBean.TableName;
    }

    @Autowired
    FileConfig config;

    IHashMapper<?> hasher = IHashMapper.of("uuid-sextuple");

    /**
     * 本地文件映射
     * */
    public File localFileOf(String uuid)
    {
        var path = hasher.hashOf(uuid).getHashValue();
        return new File(config.getFolderLocal(), path + ".bin");
    }

    /**
     * 上传文件
     * */
    public FileBean upload()
    {
        return null;
    }

    /**
     * 下载文件
     * */
    public void download()
    {
        ;
    }

}
