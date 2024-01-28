package firok.spring.plugs.component;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 * 网络请求限流器
 * */
@ConditionalOnExpression("${firok.spring.plugs.network-valve.enable:false}")
@Component
public class NetworkValveConfig
{
	public enum ValveMethod
	{
		/**
		 * 基于 redis 的限流器实现
		 * */
		Redis,
		;
	}
}
