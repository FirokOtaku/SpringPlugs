package firok.spring.plugs.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Data
@Configuration
@ConfigurationProperties("firok.spring.plugs.file")
@ConditionalOnExpression("${firok.spring.plugs.file.enable:false}")
public class FileConfig
{
    @Value("${file-local:/caches}")
    File folderLocal;

    @Value("${auto-table-creation:false}")
    Boolean autoCreateTable;

    @PostConstruct
    void mkdirs()
    {
        if(!folderLocal.exists()) folderLocal.mkdirs();
    }
}
