package org.springframework.samples.mvc.cache;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheInterceptor implements HandlerInterceptor{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		logger.info("■■■■■■■■■■■■ 한글한글 CacheInterceptor Pre Handle");
	    return filterAndPreHandleEtag( request,  response,  handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) {
		logger.info("■■■■■■■■■■■■ CacheInterceptor Post Handle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex) {
		logger.info("■■■■■■■■■■■■ CacheInterceptor After Completion");
	}
	
	private boolean filterAndPreHandleEtag(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String ehcacheName = null;
		if(handler instanceof HandlerMethod
		   && (ehcacheName = getEhcacheName((HandlerMethod) handler))!=null) {
			String ifNoneMatch = request.getHeader("If-None-Match")==null?"":request.getHeader("If-None-Match");
			response.setHeader("ETag", getLastCacheUpdateTime(ehcacheName));
			if(ifNoneMatch.equals(getLastCacheUpdateTime(ehcacheName))) {
				response.setStatus(304);
				return false;
			}else {
				return true;
			}
		}
		return true;
	}
	
	private String getEhcacheName(HandlerMethod handlerMethod) {
		Annotation[] annotations = handlerMethod.getMethod().getAnnotations();
		for(Annotation annotation : annotations) {
			if(annotation.annotationType().getName().equals(ETagAndEHcache.class.getName())) {
				ETagAndEHcache eTagAndEHcache = (ETagAndEHcache) annotation;
				return eTagAndEHcache.ehcacheName();
			}
		}
		return null;
	}
	
	private String getLastCacheUpdateTime(String cacheName) {
		CacheManager cacheManger = CacheManager.getInstance();
		if(!cacheManger.cacheExists(cacheName)) {
			return "0";
		}
		Cache cache = cacheManger.getCache(cacheName);
		List<?> keys = cache.getKeys();
		Element element = keys.isEmpty()?null:cache.get(keys.get(0));
		return element==null?"0":String.valueOf(element.getLastUpdateTime());
	}
}
