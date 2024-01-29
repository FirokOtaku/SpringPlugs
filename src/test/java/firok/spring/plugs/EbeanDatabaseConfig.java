package firok.spring.plugs;

import io.ebean.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class EbeanDatabaseConfig
{
    @Autowired
    DataSource ds;

    @Bean
    public Database Database()
    {
        var config = new io.ebean.config.DatabaseConfig();
        config.setDataSource(ds);
        return config.build();
    }
}
