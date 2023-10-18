//package firok.spring.plugs.component;
//
//
//import com.baomidou.mybatisplus.extension.service.IService;
//import firok.spring.dreamspeak.CodeException;
//import firok.spring.dreamspeak.Errors;
//import firok.spring.dreamspeak.bean.UserBean;
//import firok.spring.dreamspeak.service.multi.TagServiceMulti;
//import firok.spring.dreamspeak.util.Times;
//import firok.topaz.Collections;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.*;
//
//@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "SpringJavaAutowiredFieldsWarningInspection"})
//@Component
//public class WoodBrewer implements HandlerInterceptor
//{
//	private static final String KEY_SIZE = "bs";
//	private static final String KEY_TOKEN = "auth";
//	@Autowired
//	EncryptConfig enc;
//
//	@Autowired
//	IService<UserBean> serviceUser;
//	@Autowired
//	TagServiceMulti serviceTag;
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
//	{
//		var now = Times.now();
//
//		var cookies = Collections.mappingKeyValue(
//				Arrays.asList(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0])),
//				Cookie::getName,
//				Cookie::getValue
//		);
//		UserBean user = null;
//		List<UserTag> listUserTag = new ArrayList<>();
////		System.out.println("preHandle() cookies");
////		System.out.println(cookies);
//		try
//		{
//			var strSize = cookies.get(KEY_SIZE);
//			var size = strSize != null ? Integer.parseInt(cookies.get(KEY_SIZE)) : 0;
//			if(size <= 0) // 不用处理了
//				CodeException.occur(Errors.COOKIE_EMPTY);
//
//			var buffer = new String[size];
//			for(var step = 0; step < size; step++)
//			{
//				var bufferPiece = cookies.get(KEY_TOKEN + step);
//				if(bufferPiece == null)
//					CodeException.occur(Errors.COOKIE_VALUE_EMPTY);
//				buffer[step] = bufferPiece;
//			}
//			var token = Jsons.read(enc.dec(buffer), CookieToken.class);
//
//			var expireTime = token.getTimeExpire();
//			if(now > expireTime)
//				CodeException.occur(Errors.TOKEN_EXPIRED);
//
//			var ip = token.getAccessIpAddress();
//			if(!Objects.equals(request.getRemoteHost(), ip))
//				CodeException.occur(Errors.TOKEN_INVALID);
//
//			var uid = token.getUid();
//			user = serviceUser.getById(uid);
//			if(user == null)
//				CodeException.occur(Errors.USER_NOT_FOUND);
//			if(user.getTimestampExpire() != null)
//				CodeException.occur(Errors.USER_DISABLED);
//
//			listUserTag = serviceTag.getUserTags(uid);
//		}
//		catch (Exception e)
//		{
////			System.err.println("Cookie 错误");
////			System.err.println(e.getLocalizedMessage());
////			e.printStackTrace(System.err);
//		}
//		finally
//		{
//			request.setAttribute(ATTR_NOW, now);
//			request.setAttribute(ATTR_COOKIES, cookies);
//			setCurrentUser(user);
//			setCurrentUserTags(listUserTag);
//		}
//
//		return true;
//	}
//
//	@Value("${dreamspeak.token-expire-time:3600}")
//	int tokenExpireTime;
//
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
//	{
//		var user = getCurrentUser();
////		System.out.println("postHandle() user");
////		System.out.println(user);
//		var listCookieAdd = new ArrayList<Cookie>(12);
//		if(user == null) // 清空 cookie
//		{
//			var cookieSize = new Cookie(KEY_SIZE, "");
//			cookieSize.setMaxAge(0);
//			cookieSize.setSecure(false);
//			cookieSize.setPath("/");
//
//			for(var step = 0; step < 10; step++)
//			{
//				var cookie = new Cookie(KEY_TOKEN + step, "");
//				cookie.setMaxAge(0);
//				cookie.setSecure(false);
//				cookie.setPath("/");
//				listCookieAdd.add(cookie);
////				response.addCookie(cookie);
//			}
//		}
//		else
//		{
//			var now = (long) request.getAttribute(ATTR_NOW);
//			var token = new CookieToken();
//			token.setUid(user.getId());
//			token.setTimeExpire(now + tokenExpireTime * 1000L);
//			token.setAccessIpAddress(request.getRemoteHost());
//			var text = Jsons.write(token);
//			var buffer = enc.enc(text);
//
//			var cookieSize = new Cookie(KEY_SIZE, String.valueOf(buffer.length));
//			cookieSize.setMaxAge(tokenExpireTime);
//			cookieSize.setSecure(false);
//			cookieSize.setPath("/");
////			response.addCookie(cookieSize);
//			listCookieAdd.add(cookieSize);
//
//			for(var step = 0; step < buffer.length; step++)
//			{
//				var cookie = new Cookie(KEY_TOKEN + step, buffer[step]);
//				cookie.setMaxAge(tokenExpireTime);
//				cookie.setSecure(false);
//				cookie.setPath("/");
//				listCookieAdd.add(cookie);
////				response.addCookie(cookie);
//			}
//		}
//
//		for(var cookie : listCookieAdd)
//		{
//			response.addCookie(cookie);
//		}
//	}
//
//	@Autowired
//	HttpServletRequest request;
//	@Autowired
//	HttpServletResponse response;
//
//	private static final String ATTR_NOW = "requestNow";
//	private static final String ATTR_CURRENT_USER = "requestUser";
//	private static final String ATTR_CURRENT_USER_TAG = "requestTags";
//	private static final String ATTR_COOKIES = "requestCookies";
//	public void setCurrentUser(UserBean user)
//	{
//		request.setAttribute(ATTR_CURRENT_USER, user);
//	}
//	public UserBean getCurrentUser()
//	{
//		return request.getAttribute(ATTR_CURRENT_USER) instanceof UserBean user ? user : null;
//	}
//	public void setCurrentUserTags(List<UserTag> tags)
//	{
//		request.setAttribute(ATTR_CURRENT_USER_TAG, tags == null ? new ArrayList<>(0) : tags);
//	}
//	@SuppressWarnings("unchecked")
//	public List<UserTag> getCurrentUserTags()
//	{
//		return request.getAttribute(ATTR_CURRENT_USER_TAG) instanceof List list ? list : null;
//	}
//
//	public boolean hasTag(UserTag tagValue)
//	{
//		var list = getCurrentUserTags();
//		return list != null && list.contains(tagValue);
//	}
//	public boolean hasAllTags(UserTag... tags)
//	{
//		if(tags == null || tags.length <= 0)
//			return true;
//
//		var listTags = getCurrentUserTags();
//
//		for(var tag : tags) // 进行一个普通的遍历
//		{
//			if(tag == null)
//				continue;
//
//			if(!listTags.contains(tag))
//				return false;
//		}
//
//		return true;
//	}
//	public boolean hasAnyTag(UserTag... tags)
//	{
//		if(tags == null || tags.length <= 0)
//			return true;
//
//		var listTags = getCurrentUserTags();
//
//		for(var tag : tags)
//		{
//			if(tag == null)
//				continue;
//
//			if(listTags.contains(tag))
//				return true;
//		}
//
//		return false;
//	}
//	@Deprecated(forRemoval = true)
//	public boolean isUserManager()
//	{
//		return hasTag(UserTag.Manager);
//	}
//
//	/**
//	 * 获取除了鉴权用之外的 cookie
//	 * */
//	@SuppressWarnings("unchecked")
//	public Map<String, String> getCookies()
//	{
//		var ret = new HashMap<String, String>();
//		var cookies = (Map<String, String>) request.getAttribute(ATTR_COOKIES);
//		if(cookies == null) return ret;
//
//		for(var entry : cookies.entrySet())
//		{
//			var key = entry.getKey();
//			if(key.startsWith(KEY_TOKEN) || key.equals(KEY_SIZE))
//				continue;
//			var value = entry.getValue();
//			ret.put(key, value);
//		}
//
//		return ret;
//	}
//
//	public long getNow()
//	{
//		return (long) request.getAttribute(ATTR_NOW);
//	}
//}
