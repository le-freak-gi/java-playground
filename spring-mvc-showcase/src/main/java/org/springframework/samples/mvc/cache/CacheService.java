package org.springframework.samples.mvc.cache;


public interface CacheService {
	
	public String getData();
	public String getCacheData(String cacheName);
	public void setData(CacheDto cacheDto);
}
