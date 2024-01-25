@MVCIntrospective(
        generateController = Constants.FALSE,
        templateServiceImplContent = GeneralServiceImpl.Template,
        extraParams = {
                @Param(key = "##CREATE_SET_TIMESTAMP_CREATE##", value = "true"),
                @Param(key = "##CREATE_SET_TIMESTAMP_UPDATE##", value = "true"),
                @Param(key = "##UPDATE_SET_TIMESTAMP_UPDATE##", value = "true"),
                @Param(key = "##DELETE_SET_TIMESTAMP_UPDATE##", value = "true"),
                @Param(key = "##DELETE_SET_TIMESTAMP_DELETE##", value = "false"),
                @Param(key = "##DELETE_SET_IS_DELETE##", value = "false"),
                @Param(key = "##DELETE_LOGICAL##", value = "false"),
        }
)
package firok.spring.plugs.bean;

import firok.spring.mvci.Constants;
import firok.spring.mvci.MVCIntrospective;
import firok.spring.mvci.Param;
import firok.spring.plugs.mvci.GeneralServiceImpl;
