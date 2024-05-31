package firok.spring.plugs;

import firok.spring.plugs.bean.UserBean;
import firok.spring.plugs.config.EncryptConfig;
import firok.spring.plugs.config.UserConfig;
import firok.spring.plugs.service.CompactEncryptService;
import firok.spring.plugs.service.CompactUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

import static firok.topaz.general.Collections.sizeOf;

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
				UserConfig.class,
				CompactEncryptService.class,
		}
)
public class ShaEncTests
{
	@Autowired
	UserConfig config;

	@Autowired
	CompactUserService service;

	/**
	 * 测试用户模块的加密解密功能
	 * */
	@Test
	public void test()
	{
		var rand = new Random();
		var setLenPassword = new HashSet<Integer>();
		var setLenSalt = new HashSet<Integer>();
		for(var step = 0; step < 4096; step++)
		{
			var len = 12 + rand.nextInt(8);
			var password = UUID.randomUUID().toString().substring(0, len);

			config.setPasswordSalt(false);
			{
				var user = new UserBean();
				service.processUserPassword(user, password);
				setLenPassword.add(sizeOf(user.getPassword()));
				setLenSalt.add(sizeOf(user.getPasswordSalt()));
				var matched = service.matchUser(user, password);
				Assertions.assertTrue(matched);
			}
			config.setPasswordSalt(true);
			{
				var user = new UserBean();
				service.processUserPassword(user, password);
				setLenPassword.add(sizeOf(user.getPassword()));
				setLenSalt.add(sizeOf(user.getPasswordSalt()));
				var matched = service.matchUser(user, password);
				Assertions.assertTrue(matched);
			}
		}
		System.out.println("len password");
		System.out.println(setLenPassword);
		System.out.println("len salt");
		System.out.println(setLenSalt);
	}
}
