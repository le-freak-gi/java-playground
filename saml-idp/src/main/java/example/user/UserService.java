package example.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

	public List<User> findAll();
	
	public Optional<User> findById(String userId);
	
	public User save(User user);
	
	public void deleteById(String userId);
	
	public Map<String, String> login(String userId, String userPassword);
}
