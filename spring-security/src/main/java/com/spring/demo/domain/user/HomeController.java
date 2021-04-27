package com.spring.demo.domain.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.demo.domain.user.dto.Account;
import com.spring.demo.global.config.security.CurrentUser;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class HomeController {

	@GetMapping("/")
	public String home(@CurrentUser Account user) {
		log.info("user=" + user);
		return "home";
	}
	
	@GetMapping("/hello")
	public void hello(@AuthenticationPrincipal(expression = "account") Account user) {
		log.info("user=" + user);
	}
	
	@GetMapping("/login")
	public void login() {}
	
}
