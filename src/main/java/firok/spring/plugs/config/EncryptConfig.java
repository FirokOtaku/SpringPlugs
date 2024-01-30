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
@ConfigurationProperties(prefix = "firok.spring.plugs.encrypt")
@ConditionalOnExpression("${firok.spring.plugs.encrypt.enable:false}")
public class EncryptConfig
{
    @Value("${private-key-path:./private-key.bin}")
    public File pathPrivateKey;

    @Value("${public-key-path:./public-key.bin}")
    public File pathPublicKey;

    @Value("${private-key-value:}")
    private String keyValuePrivate;

    @Value("${public-key-value:}")
    private String keyValuePublic;

    @PostConstruct
    void mkdirs()
    {
        pathPrivateKey.getParentFile().mkdirs();
        pathPublicKey.getParentFile().mkdirs();
    }
}
