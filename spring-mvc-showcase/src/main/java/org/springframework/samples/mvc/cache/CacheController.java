package org.springframework.samples.mvc.cache;



import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/cache")
public class CacheController {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CacheService cacheService; 
	
	@GetMapping
	public void cache(Model model) {
		logger.info("cache");
		model.addAttribute("data", cacheService.getData());
	}
	
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public ResponseEntity<Object> getData() {
		logger.info("■■■■■■■■■■■■ CacheController cache/data");
		return ResponseEntity.ok(cacheService.getData());
	}
	
	@ETagAndEHcache(ehcacheName="cacheData")
	@RequestMapping(value = "/etag", method = RequestMethod.GET)
	public ResponseEntity<Object> getEtagData() {
		logger.info("■■■■■■■■■■■■ CacheController cache/etag");
		return ResponseEntity.ok(cacheService.getCacheData("test"));
	}
	
	@ETagAndEHcache(ehcacheName="cacheData")
	@RequestMapping(value = "/post-etag", method = RequestMethod.POST)
	public ResponseEntity<Object> getEtagDataWithPost() {
		logger.info("■■■■■■■■■■■■ CacheController cache/etag");
		return ResponseEntity.ok(cacheService.getCacheData("test"));
	}
	
	@RequestMapping(value = "/new-data", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> setData(@RequestBody CacheDto cacheDto) {
		logger.info("■■■■■■■■■■■■ CacheController cache/new-data");
		cacheService.setData(cacheDto);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", cacheService.getCacheData(cacheDto.getKey()));
		return new ResponseEntity<>(result, HttpStatus.valueOf(200));
	}

}
