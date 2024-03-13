package firok.spring.plugs;

import firok.spring.plugs.bean.FileBean;
import firok.spring.plugs.bean.TagBean;
import firok.spring.plugs.bean.UserBean;
import firok.spring.plugs.bean.query.QTagBean;
import firok.spring.plugs.bean.query.QUserBean;
import firok.spring.plugs.config.*;
import firok.spring.plugs.service.*;
import firok.topaz.database.Databases;
import io.ebean.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.util.Arrays.sizeOf;

@TestPropertySource("classpath:application.yml")
@SpringBootTest(
        classes = {
                EbeanDatabaseConfig.class,
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
    DataSource ds;
    @Autowired
    Database db;

    @Test
    void testEbeanFunctions() throws Exception
    {
        try(var conn = ds.getConnection())
        {
            try(var context = Databases.executeQuery(conn, "show tables");
                var rs = context.rs())
            {
                var tables = Databases.collectField(rs, 1, String.class);
                Assertions.assertTrue(tables.contains(FileBean.TableName));
                Assertions.assertTrue(tables.contains(UserBean.TableName));
                Assertions.assertTrue(tables.contains(TagBean.TableName));
            }

            final int countFileInit = db.createQuery(FileBean.class).findCount();
            final int countUserInit = db.createQuery(UserBean.class).findCount();
            final int countTagInit = db.createQuery(TagBean.class).findCount();

            var file = new FileBean();
            file.setId(UUID.randomUUID().toString());
            file.setFileName("123");
            file.setFileSize(123L);
            db.save(file);

            final int countFileNow = db.createQuery(FileBean.class).findCount();
            Assertions.assertEquals(countFileInit + 1, countFileNow);

            db.createQuery(FileBean.class).setId(file.getId()).delete();
            Assertions.assertEquals(countFileInit, db.createQuery(FileBean.class).findCount());

            var user = new UserBean();
            user.setId(UUID.randomUUID().toString());
            user.setUsername("123");
            user.setPassword("123".getBytes(StandardCharsets.UTF_8));
            user.setNickname("123");
            user.setTokenCookie("123");
            db.save(user);

            final int countUserNow = db.createQuery(UserBean.class).findCount();
            Assertions.assertEquals(countUserInit + 1, countUserNow);
            var userQuery = db.createQuery(UserBean.class).setId(user.getId()).findOne();
            Assertions.assertNotNull(userQuery);
            Assertions.assertEquals(user, userQuery);

            db.createQuery(UserBean.class).setId(user.getId()).delete();
            Assertions.assertEquals(countUserInit, db.createQuery(UserBean.class).findCount());

            var tag = new TagBean();
            tag.setId(UUID.randomUUID().toString());
            tag.setTagType("123");
            tag.setTargetId("123");
            tag.setTagValue("123");
            db.save(tag);

            final int countTagNow = db.createQuery(TagBean.class).findCount();
            Assertions.assertEquals(countTagInit + 1, countTagNow);

            db.createQuery(TagBean.class).setId(tag.getId()).delete();
            Assertions.assertEquals(countTagInit, db.createQuery(TagBean.class).findCount());
        }
    }

    @Autowired
    CompactFileService serviceFile;
    @Autowired
    CompactTagService serviceTag;
    @Autowired
    CompactUserService serviceUser;
    @Autowired
    CompactEncryptService serviceEncrypt;

    @Test
    void testTagService()
    {
        new QTagBean().delete(); // 清空所有标签信息先

        serviceTag.setTargetTags("t1", "123", "1", "2", "3");

        var listTagRaw = new QTagBean()
                .tagType.eq("t1")
                .targetId.eq("123")
                .findList()
                .stream()
                .map(TagBean::getTagValue)
                .sorted(String::compareTo)
                .toList();
        Assertions.assertEquals(3, listTagRaw.size());
        Assertions.assertEquals("1", listTagRaw.get(0));
        Assertions.assertEquals("2", listTagRaw.get(1));
        Assertions.assertEquals("3", listTagRaw.get(2));

        var tags1 = new ArrayList<>(serviceTag.getTargetTags("t1", "123"));
        tags1.sort(String::compareTo);
        Assertions.assertEquals(3, tags1.size());
        Assertions.assertEquals("1", tags1.get(0));
        Assertions.assertEquals("2", tags1.get(1));
        Assertions.assertEquals("3", tags1.get(2));

        serviceTag.setTargetTags("t1", "123");
        Assertions.assertEquals(0, new QTagBean()
                .tagType.eq("t1")
                .targetId.eq("123")
                .findCount());

        var tags2 = serviceTag.getTargetTags("t1", "123");
        Assertions.assertEquals(0, tags2.size());
    }

    @Test
    void testUserService()
    {
        new QUserBean().delete(); // 清空所有用户信息先

        Assertions.assertNull(serviceUser.getUserByUsername("123"));

        var uid = serviceUser.createUser("u1", "n1", "p1").getId();

        Assertions.assertEquals(1, new QUserBean().findCount());
        var user1 = new QUserBean().id.eq(uid).findOne();
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("u1", user1.getUsername());
        Assertions.assertEquals("n1", user1.getNickname());
        Assertions.assertTrue(serviceUser.matchUser(user1, "p1"));

        Assertions.assertNotNull(user1.getTokenCookie());

        Assertions.assertTrue(serviceUser.updateCookieToken(uid));
        var user2 = new QUserBean().id.eq(uid).findOne();
        Assertions.assertNotNull(user2);
        var tokenNew = user2.getTokenCookie();
        Assertions.assertNotNull(tokenNew);
        Assertions.assertNotEquals(user1.getTokenCookie(), tokenNew);

        Assertions.assertNotNull(serviceUser.checkPassword("u1", "p1"));

        Assertions.assertTrue(serviceUser.deleteUserById(uid));

        Assertions.assertEquals(0, new QUserBean().findCount());
    }

    @Test
    void testEncryptService()
    {
        var raw1 = "123456789";
        var enc1 = serviceEncrypt.enc(raw1);
        Assertions.assertNotNull(enc1);
        Assertions.assertNotEquals(0, sizeOf(enc1));
        var dec1 = serviceEncrypt.dec(enc1);
        Assertions.assertEquals(raw1, dec1);
    }
}
