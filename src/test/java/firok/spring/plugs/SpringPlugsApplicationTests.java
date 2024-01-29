package firok.spring.plugs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import firok.spring.plugs.config.*;
import firok.spring.plugs.service_compact.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import javax.sql.DataSource;

@SpringBootTest(
        classes = {
                DataSourceConfig.class,
                SpringPlugs.class,
                EncryptConfig.class,
                FileConfig.class,
                TagConfig.class,
                UserConfig.class,
                CompactFileService.class,
                CompactTagService.class,
                CompactUserService.class,
                CompactEncryptService.class,
        }
)
class SpringPlugsApplicationTests
{
    @Autowired
    SpringPlugs plugs;

    @Autowired
    EncryptConfig configEncrypt;
    @Autowired
    FileConfig configFile;
    @Autowired
    TagConfig configTag;
    @Autowired
    UserConfig configUser;

    @Autowired
    CompactFileService serviceFile;
    @Autowired
    CompactTagService serviceTag;
    @Autowired
    CompactUserService serviceUser;
    @Autowired
    CompactEncryptService serviceEncrypt;

    @Test
    void contextLoads()
    {
//        System.out.println(serviceFile);
//        System.out.println(serviceTag);
//        System.out.println(serviceUser);
//        System.out.println(serviceEncrypt);
    }

}
