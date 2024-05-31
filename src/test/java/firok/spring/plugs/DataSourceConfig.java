package firok.spring.plugs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig
{
	@Value("${spring.datasource.driver-class-name}")
	String driverClassName;
	@Value("${spring.datasource.url}")
	String jdbcUrl;
	@Value("${spring.datasource.username}")
	String username;
	@Value("${spring.datasource.password}")
	String password = "";

	@Value("${spring.datasource.hikari.minimum-idle}")
	Integer minimumIdle;
	@Value("${spring.datasource.hikari.idle-timeout}")
	Long idleTimeout;
	@Value("${spring.datasource.hikari.maximum-pool-size}")
	Integer maximumPoolSize;
	@Value("${spring.datasource.hikari.auto-commit}")
	Boolean autoCommit;
	@Value("${spring.datasource.hikari.pool-name}")
	String poolName;
	@Value("${spring.datasource.hikari.max-lifetime}")
	Long maxLifetime;
	@Value("${spring.datasource.hikari.connection-timeout}")
	Long connectionTimeout;
	@Value("${spring.datasource.hikari.connection-test-query}")
	String connectionTestQuery;

	@Bean
	public DataSource ds()
	{
		var config = new HikariConfig();
		config.setJdbcUrl(jdbcUrl);
		config.setUsername(username);
		config.setPassword(password);
		config.setDriverClassName(driverClassName);
		config.setMinimumIdle(minimumIdle);
		config.setIdleTimeout(idleTimeout);
		config.setMaximumPoolSize(maximumPoolSize);
		config.setAutoCommit(autoCommit);
		config.setPoolName(poolName);
		config.setMaxLifetime(maxLifetime);
		config.setConnectionTimeout(connectionTimeout);
		config.setConnectionTestQuery(connectionTestQuery);
		return new HikariDataSource(config);
	}
}
