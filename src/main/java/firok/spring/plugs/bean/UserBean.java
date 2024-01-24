package firok.spring.plugs.bean;

import firok.spring.mvci.Constants;
import firok.spring.mvci.MVCIntrospective;
import firok.spring.mvci.Param;
import firok.spring.plugs.mvci.GeneralServiceImpl;
import lombok.Data;

/**
 * 用户数据
 * */
@Data
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
public class UserBean extends ChainedIdStringTimestampLongBean
{
    /**
     * 用户名
     * */
    String username;
    /**
     * 昵称
     * */
    String nickname;
    /**
     * 密码
     * */
    String password;
    /**
     * 二步验证密钥
     * */
    String token2Fa;
    /**
     * 用户 token 加盐
     * */
    String tokenUser;
}
