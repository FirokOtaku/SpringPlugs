package firok.spring.plugs.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("firok.spring.plugs.user")
@ConditionalOnExpression("${firok.spring.plugs.user.enable:false}")
public class UserConfig
{
    @Value("${auto-table-creation:false}")
    Boolean autoCreateTable;

    @Value("${password-salt:}")
    Boolean passwordSalt;
}
