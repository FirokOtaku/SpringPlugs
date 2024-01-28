package firok.spring.plugs.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "firok.spring.plugs.tag")
@ConditionalOnExpression("${firok.spring.plugs.tag.enable:false}")
public class TagConfig
{
}
