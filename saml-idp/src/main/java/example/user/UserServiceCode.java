package example.user;

public enum UserServiceCode {
	USER_SERVICE("USER_SERVICE"), USER_NO("USER_NO"), LOGON("00"), PASSWORD_MISMATCH("01"), ID_NOT_EXISTS("02"), LOCKED("03");
	private String value;
	public static final int FAIL_LIMIT = 10;
	private UserServiceCode(String value) {
		this.value=value;
	}
	public String value() {
		return this.value;
	}
}
