package firok.spring.plugs;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan("firok.spring.plugs.mapper")
public class MybatisPlusConfig
{
    @Autowired
    MybatisConfiguration mybatisConfiguration;
    @Autowired
    GlobalConfig globalConfig;
    @Autowired
    DataSource ds;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(ds);
        sqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    @Value("${spring.datasource.url}")
    String url;

    /**
     * 分页插件
     * <p>
     * https://baomidou.com/pages/2976a3/#spring-boot
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor()
    {
        final DbType type;
        if (url.startsWith("jdbc:mysql"))
            type = DbType.MYSQL;
        else if (url.startsWith("jdbc:h2"))
            type = DbType.H2;
        else
            throw new IllegalArgumentException("不支持的目标数据库: " + url);

        var interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(type));
        return interceptor;
    }
}
