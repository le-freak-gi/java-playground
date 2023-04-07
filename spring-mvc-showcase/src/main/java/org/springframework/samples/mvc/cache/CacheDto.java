package org.springframework.samples.mvc.cache;

public class CacheDto {
	String key;
	String data;
	
	CacheDto(){
		
	}
	
	CacheDto(String key){
		this.key=key;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
