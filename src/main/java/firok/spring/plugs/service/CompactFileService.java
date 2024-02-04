package firok.spring.plugs.service;

import firok.spring.plugs.bean.FileBean;
import firok.spring.plugs.bean.query.QFileBean;
import firok.spring.plugs.config.FileConfig;
import firok.spring.plugs.util.TableUtil;
import firok.topaz.hash.IHashMapper;
import io.ebean.DB;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
@ConditionalOnBean(FileConfig.class)
public class CompactFileService extends AbstractCompactService
{
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

    @Override
    protected boolean shouldAutoCreateTable()
    {
        return config.getAutoCreateTable();
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
    public File localFileOf(FileBean file)
    {
        return localFileOf(file.getId());
    }

    /**
     * 上传文件
     * */
    public FileBean uploadFile(
            String fileName,
            InputStreamSource source
    ) throws Exception
    {
        var id = UUID.randomUUID().toString();
        var fileLocal = localFileOf(id);
        var folderLocal = fileLocal.getParentFile();
        if(!folderLocal.exists()) folderLocal.mkdirs();
        try
        {
            long fileSize;
            try(var ifs = source.getInputStream();
                var ofs = new FileOutputStream(fileLocal))
            {
                fileSize = ifs.transferTo(ofs);
            }

            var file = new FileBean();
            file.setId(id);
            file.setFileSize(fileSize);
            DB.save(file);

            return file;
        }
        catch (Exception any)
        {
            fileLocal.delete();
            throw any;
        }
    }

    /**
     * 下载文件
     * */
    public FileBean findFile(String id)
    {
        var file = new QFileBean().id.eq(id).findOne();
        if(file == null)
            throw new IllegalArgumentException("file info not found: " + id);
        return file;
    }

    /**
     * 删除文件
     * */
    public void deleteFile(String id)
    {
        new QFileBean().id.eq(id).delete();
        localFileOf(id).delete(); // todo 清理空文件夹
    }

}
