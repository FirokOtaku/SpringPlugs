package firok.spring.plugs.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("firok.spring.plugs.user")
@ConditionalOnExpression("${firok.spring.plugs.user.enable:false}")
public class UserConfig
{
}