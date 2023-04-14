package example.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService{
	
    private final UserRepository userRespository;
    
    public UserServiceImpl(UserRepository userRespository) {
        this.userRespository = userRespository;
    }
    
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
    public List<User> findAll() {
        return userRespository.findAll();
    }
    
    @Override
    public Optional<User> findById(String userId) {
        return userRespository.findById(userId);
    }
    
    @Override
    public User save(User user) {
        return userRespository.save(user);
    }
    
    @Override
    public void deleteById(String userId) {
    	userRespository.deleteById(userId);
    }

	@Override
	public Map<String, String> login(User user) {
		Map<String, String> rtnMap = new HashMap<String,String>();
		if(user==null||user.getUserId()==null||user.getUserId().isEmpty()||user.getUserPassword()==null||user.getUserPassword().isEmpty()) {
			rtnMap.put(UserServiceCode.USER_SERVICE.value(), UserServiceCode.INVALID_ID_OR_PASSWORD.value());
		}else {
			Optional<User> selectedUser = findById(user.getUserId()); 
			if(selectedUser.isEmpty()) {
				rtnMap.put(UserServiceCode.USER_SERVICE.value(), UserServiceCode.ID_NOT_EXISTS.value());
			}else {
				if(selectedUser.get().getFailedCount()==UserServiceCode.FAIL_LIMIT) {
					rtnMap.put(UserServiceCode.USER_SERVICE.value(), UserServiceCode.LOCKED.value());
					rtnMap.put(UserServiceCode.USER_NO.value(), selectedUser.get().getUserNo());
				}else {
					if(selectedUser.get().getUserPassword().equals(user.getUserPassword())) {
						rtnMap.put(UserServiceCode.USER_SERVICE.value(), UserServiceCode.LOGON.value());
						rtnMap.put(UserServiceCode.USER_NO.value(), selectedUser.get().getUserNo());
						selectedUser.get().setFailedCount(0);
						save(selectedUser.get());
					}else {
						rtnMap.put(UserServiceCode.USER_SERVICE.value(), UserServiceCode.PASSWORD_MISMATCH.value());
						selectedUser.get().setFailedCount(selectedUser.get().getFailedCount()+1);
						save(selectedUser.get());
					}
				}
			}
		}
		return rtnMap;
	}
}
