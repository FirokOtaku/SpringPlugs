package firok.spring.plugs;

import firok.topaz.general.ProgramMeta;
import firok.topaz.general.Version;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScans({
        @ComponentScan("firok.spring.plugs.config"),
        @ComponentScan("firok.spring.plugs.component"),
        @ComponentScan("firok.spring.plugs.service"),
        @ComponentScan("firok.spring.plugs.controller"),
})
public class SpringPlugs
{
    public static final ProgramMeta META = new ProgramMeta(
            "firok.spring.plugs",
            "Spring Plugs",
            new Version(0, 23, 0, "j21"),
            "personal Spring Boot utility library",
            List.of("Firok"),
            List.of("https://github.com/FirokOtaku/SpringPlugs"),
            List.of("https://github.com/FirokOtaku/SpringPlugs"),
            "MuLanPSL-2.0"
    );
}
