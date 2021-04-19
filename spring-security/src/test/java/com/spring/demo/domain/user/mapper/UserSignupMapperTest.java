package com.spring.demo.domain.user.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.spring.demo.domain.user.dto.User;
import com.spring.demo.domain.user.dto.UserAuthority;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",	
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
@Log4j
public class UserSignupMapperTest {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserSignupMapper mapper;
	
	@Test
	public void createUser() {
		User user = User.builder()
						.username("test01")
						.password(passwordEncoder.encode("test01"))
						.enabled(Boolean.TRUE)
						.build();
		
		mapper.insertUser(user);
		
		log.info("user=" + user);	// generated key 확인 가능
		
		UserAuthority userAuthority = UserAuthority.builder()
												   .userId(user.getId())
												   .authority("USER")
												   .build();
		
		mapper.insertUserAuthority(userAuthority);
	}
	
	
}
