package org.springframework.samples.mvc.cache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service("cacheService")
public class CacheServiceImpl implements CacheService{

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public CacheServiceImpl() {
		CacheServiceImpl.data = "default data";
		CacheManager cacheManger = CacheManager.getInstance();
		Cache cache = cacheManger.getCache("cacheData");
		Element element = new Element("test", CacheServiceImpl.data );
		cache.put(element);
	}
	
	private static String data;
	
	@Override
	public synchronized String getData() {
		return CacheServiceImpl.data;
	}
	
	@Cacheable(value = "cacheData", key = "#cacheName")
	@Override
	public synchronized String getCacheData(String cacheName) {
		logger.info("■■■■■■■■■■■■■■■■■ getCacheData");
		return CacheServiceImpl.data;
	}
	
	
	@CacheEvict(value = "cacheData", allEntries=true)
	@Override
	public synchronized void setData(CacheDto cacheDto) {
		CacheServiceImpl.data = cacheDto.data;
	}



}
