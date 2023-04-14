package example.user;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserService userService; 
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> login(User user) {
		Map<String,String> resultMap = userService.login(user);
		logger.info("###################################"+resultMap);
		return ResponseEntity.ok(resultMap);
	}
	
	@RequestMapping(value = "/login2", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> loginJson(@RequestBody User user) {
		Map<String,String> resultMap = userService.login(user);
		logger.info("###################################"+resultMap);
		return ResponseEntity.ok(resultMap);
	}
}
