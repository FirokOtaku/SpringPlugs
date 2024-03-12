package firok.spring.plugs;

import firok.spring.plugs.config.EncryptConfig;
import firok.spring.plugs.service.CompactEncryptService;
import firok.spring.plugs.service.CompactUserService;
import firok.topaz.general.Encrypts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.security.spec.RSAPublicKeySpec;

/**
 * 一些测试 SHA 散列相关的代码
 * */
@TestPropertySource("classpath:application.yml")
@SpringBootTest(
		classes = {
				EbeanDatabaseConfig.class,
				DataSourceConfig.class,
				SpringPlugs.class,
				EncryptConfig.class,
				CompactEncryptService.class,
		}
)
public class ShaEncTests
{
	@Autowired
	EncryptConfig config;

	@Test
	public void test()
	{
		// Encrypts.encodeRSA("123", "");
	}
}
