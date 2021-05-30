package com.spring.demo.domain.user.mapper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.spring.demo.domain.user.model.User;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",	
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
@Log4j
public class UserMapperTests {

	@Autowired
	UserMapper mapper;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Test
	public void createUserTest() {
		User user = new User();
		user.setUsername("test");
		user.setPassword(encoder.encode("1234"));
		user.setRoles("ROLE_USER");
		
		mapper.createUser(user);
		log.info("create complete!");
	}

}
