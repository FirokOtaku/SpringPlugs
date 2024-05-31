package firok.spring.plugs;

import firok.topaz.general.CodeExceptionThrower;
import firok.topaz.general.I18N;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public enum PlugsExceptions implements CodeExceptionThrower
{
    UserNotFound(7001),
    PasswordNotMatch(7002),
    PasswordNotNull(7003),
    UsernameNotNull(7004),
    NicknameNotNull(7005),
    UsernameAlreadyExist(7006),
    UserModuleInitFail(7007),

    WriteCookieTokenFail(7011),
    ReadCookieTokenFail(7012),
    TokenFormatError(7013),
    TokenTypeError(7014),
    TokenNotMatch(7015),
    TokenExpired(7016),

    ;
    public final int code;
    PlugsExceptions(int code)
    {
        this.code = code;
    }

    @Override
    public int getExceptionCode()
    {
        return code;
    }

    private static final I18N i18n = new I18N("/firok/spring/plugs/errors", Locale.CHINA, SpringPlugs.class);
    @Override
    public @Nullable I18N getI18N()
    {
        return i18n;
    }
}
