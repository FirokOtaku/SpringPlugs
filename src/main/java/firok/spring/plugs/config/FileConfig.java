package firok.spring.plugs.config;

import lombok.Data;
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
    File folderLocal;
}
