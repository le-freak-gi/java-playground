package example.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {
	
	@Id
	@Column(nullable = false)
	private String userId;
	
	@GeneratedValue
	private String userNo;
	
	@Column(nullable = false)
	private String userPassword;
	
	private int failedCount;
	
	public User(){
		
	}
	
	public User(String userId, String userPassword){
		this.userId = userId;
		this.userPassword = userPassword;
	}
	
	public User(String userId, String userNo, String userPassword, int failedCount){
		this.userId = userId;
		this.userNo = userNo;
		this.userPassword = userPassword;
		this.failedCount = failedCount;
	}
	
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}
}
