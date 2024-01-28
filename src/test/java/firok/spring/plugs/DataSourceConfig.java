package firok.spring.plugs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig
{
	@Bean
	public DataSource ds()
	{
		var config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/spring-plugs?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8");
		config.setUsername("root");
		config.setPassword("root");
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return new HikariDataSource(config);
	}
}
