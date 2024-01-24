package firok.spring.plugs;

import firok.topaz.general.CodeExceptionThrower;
import firok.topaz.general.I18N;
import org.jetbrains.annotations.Nullable;

public enum PlugsExceptions implements CodeExceptionThrower
{
    UserNotFound(7001),
    PasswordNotMatch(7002),
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

    @Override
    public @Nullable I18N getI18N()
    {
        return null;
    }
}
