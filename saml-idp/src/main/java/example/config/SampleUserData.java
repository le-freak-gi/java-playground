package example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import example.user.User;

@Component
public class SampleUserData {

    private static final Logger log = LoggerFactory.getLogger(SampleUserData.class);
 
    private final JdbcTemplate jdbcTemplate;
 
    public SampleUserData(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
 
    @Bean
    public void dataInsertAndRetrieve() {
		
        jdbcTemplate.update("INSERT INTO USER (USER_ID, USER_NO, USER_PASSWORD, FAILED_COUNT) VALUES (?, ?, ?, ?)", "abc1", "1", "12345", 0); 
        jdbcTemplate.update("INSERT INTO USER (USER_ID, USER_NO, USER_PASSWORD, FAILED_COUNT) VALUES (?, ?, ?, ?)", "abc2", "2", "12345", 0); 
        jdbcTemplate.update("INSERT INTO USER (USER_ID, USER_NO, USER_PASSWORD, FAILED_COUNT) VALUES (?, ?, ?, ?)", "abc3", "3", "12345", 0); 
        jdbcTemplate.update("INSERT INTO USER (USER_ID, USER_NO, USER_PASSWORD, FAILED_COUNT) VALUES (?, ?, ?, ?)", "abc4", "4", "12345", 0); 
        jdbcTemplate.update("INSERT INTO USER (USER_ID, USER_NO, USER_PASSWORD, FAILED_COUNT) VALUES (?, ?, ?, ?)", "abc5", "5", "12345", 0); 
        log.info("### user data inserted ###");
		
        jdbcTemplate.query("select * from USER", (rs, row) -> new User(
            rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4))
            ).forEach(user -> log.info("### " + user.getUserId() +" in the database. ###"));
    }
}
