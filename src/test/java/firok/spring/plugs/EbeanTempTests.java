package firok.spring.plugs;

import firok.topaz.database.Databases;
import firok.topaz.function.Conditions;
import firok.topaz.function.MayRunnable;
import io.ebean.DB;
import io.ebean.PagedList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.util.Date;

@TestPropertySource("classpath:application.yml")
@SpringBootTest(
        classes = {
                EbeanDatabaseConfig.class,
                DataSourceConfig.class,
                SpringPlugs.class,
        }
)
public class EbeanTempTests
{
    @Autowired
    DataSource ds;

    public void printPage(PagedList<?> page)
    {
        System.out.println("pageIndex: " + page.getPageIndex());
        System.out.println("pageSize: " + page.getPageSize());
        System.out.println("totalPageCount: " + page.getTotalPageCount());
        System.out.println("totalCount: " + page.getTotalCount());
        System.out.println("list: " + page.getList());
    }

    public void queryPage(int firstRow, int maxRow)
    {
        System.out.println("\n\npage - " + firstRow + " ~ " + maxRow);
        var page0 = DB.find(TestBean.class)
                .orderById(true)
                .setFirstRow(firstRow)
                .setMaxRows(maxRow)
                .findPagedList();
        printPage(page0);
    }

    @Test
    public void testPagination() throws Exception
    {
        Conditions.onlyRun((MayRunnable) () -> {
            final boolean shouldReInsert = false;
            if(shouldReInsert)
            {
                try(var conn = ds.getConnection();
                    var stmt = conn.prepareStatement("insert into d_ebean_pagination(id) values(?)");
                )
                {
                    Databases.executeUpdate(conn, "delete from d_ebean_pagination");
                    for(var step = 0; step < 50; step++)
                    {
                        stmt.setInt(1, step);
                        stmt.execute();
                    }
                }
            }

            queryPage(1, 5);
            queryPage(0, 5);
            queryPage(10, 10);
            queryPage(60, 1);
            queryPage(1, 0);
            queryPage(50, 50);
            queryPage(1, 10);
            queryPage(2, 10);
        }, Conditions.whenBefore(new Date(1717171200000L))); // 2024-06-01 00:00:00
    }
}
