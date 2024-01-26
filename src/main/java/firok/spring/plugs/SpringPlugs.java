package firok.spring.plugs;

import firok.topaz.general.ProgramMeta;
import firok.topaz.general.Version;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@MapperScan("firok.spring.plugs.mapper")
@ComponentScans({
        @ComponentScan("firok.spring.plugs.config"),
        @ComponentScan("firok.spring.plugs.component"),
        @ComponentScan("firok.spring.plugs.mapper"),
        @ComponentScan("firok.spring.plugs.service"),
        @ComponentScan("firok.spring.plugs.service_impl"),
        @ComponentScan("firok.spring.plugs.controller"),
        @ComponentScan("firok.spring.plugs.service_compact"),
})
public class SpringPlugs
{
    public static final ProgramMeta META = new ProgramMeta(
            "firok.spring.plugs",
            "Spring Plugs",
            new Version(0, 12, 0, "j21"),
            "personal Spring Boot utility library",
            List.of("Firok"),
            List.of("https://github.com/FirokOtaku/SpringPlugs"),
            List.of("https://github.com/FirokOtaku/SpringPlugs"),
            "MuLanPSL-2.0"
    );
}
