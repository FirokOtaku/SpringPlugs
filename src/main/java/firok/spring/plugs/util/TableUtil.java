package firok.spring.plugs.util;

import firok.spring.plugs.PlugsExceptions;
import firok.spring.plugs.bean.UserBean;
import firok.spring.plugs.bean.property.*;
import firok.topaz.database.Databases;
import org.intellij.lang.annotations.Language;

import javax.sql.DataSource;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TableUtil
{
    public static Map<String, Class<?>> scanFields(
            Class<?> beanClass
    )
    {
        var ret = new HashMap<String, Class<?>>();
        Class<?> classNow = beanClass;
        while(classNow != Object.class)
        {
            for(var gi : classNow.getGenericInterfaces())
            {
                if(gi instanceof ParameterizedType pt && pt.getRawType() instanceof Class<?> ptc)
                {
                    if((ptc == ChainedIdProperty.class || ptc == IdProperty.class) && pt.getActualTypeArguments()[0] instanceof Class<?> atac)
                        ret.put("id", atac);
                    else if(ptc == ChainedIsDeleteProperty.class || ptc == IsDeleteProperty.class)
                        ret.put("is_delete", Boolean.class);
                    else if(ptc == ChainedTimestampCreateDateProperty.class || ptc == TimestampCreateDateProperty.class)
                        ret.put("timestamp_create", Date.class);
                    else if(ptc == ChainedTimestampCreateLongProperty.class || ptc == TimestampCreateLongProperty.class)
                        ret.put("timestamp_create", Long.class);
                    else if(ptc == ChainedTimestampDeleteDateProperty.class || ptc == TimestampDeleteDateProperty.class)
                        ret.put("timestamp_delete", Date.class);
                    else if(ptc == ChainedTimestampDeleteLongProperty.class || ptc == TimestampDeleteLongProperty.class)
                        ret.put("timestamp_delete", Long.class);
                    else if(ptc == ChainedTimestampUpdateDateProperty.class || ptc == TimestampUpdateDateProperty.class)
                        ret.put("timestamp_update", Date.class);
                    else if(ptc == ChainedTimestampUpdateLongProperty.class || ptc == TimestampUpdateLongProperty.class)
                        ret.put("timestamp_update", Long.class);
                }
            }

            classNow = classNow.getSuperclass();
        }
        return ret;
    }

//    public static String generateTableForMysql(String tableName, Map<String, Class<?>> mapField)
//    {
//        var ret = new StringBuilder();
//        ret.append("create table if not exists").append(tableName).append("(\n");
//
//        for(var entryField : mapField.entrySet())
//        {
//            var nameField = entryField.getKey();
//            var typeField = entryField.getValue();
//
//            ret.append(nameField).append(" ");
//        }
//
//        ret.append(")");
//        return ret.toString();
//    }

    @SuppressWarnings("SqlNoDataSourceInspection")
    @Language("SQL")
    public static final String SqlShowTablesMysql = """
            show tables
            """;

    public static void createTableIfNotExist(DataSource ds, String tableName, String sqlShowTables, String sqlCreateTable) throws SQLException
    {
        try(var conn = ds.getConnection();
            var context = Databases.executeQuery(conn, sqlShowTables);
            var rs = context.rs())
        {
            var listTable = Databases.collectField(rs, 0, String.class);
            if(!listTable.contains(tableName))
            {
                try (var ignored = Databases.executeUpdate(conn, sqlCreateTable)) { }
            }
        }
    }
}
