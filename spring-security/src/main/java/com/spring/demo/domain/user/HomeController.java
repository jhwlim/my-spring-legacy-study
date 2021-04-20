package com.spring.demo.domain.user;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.demo.domain.user.dto.Account;
import com.spring.demo.global.config.security.SecurityUser;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/hello")
	public void hello(Authentication authentication2, 
					  @AuthenticationPrincipal SecurityUser securityUser
					  ) {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		log.info("principal : " + (SecurityUser) authentication.getPrincipal());
//		log.info("2 : " + authentication2.getPrincipal());
		log.info("securityUser : " + securityUser);
//		log.info("?=" + securityUser);
//		log.info("user=" + account);
	}
	
	@GetMapping("/login")
	public void login() {}
	
	@GetMapping("/admin")
	public void getAdminPage() {}
	
	@GetMapping("/error")
	public String error() {
		return "redirect:/";
	}
	
}
