package com.spring.demo.domain.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/hello")
	public void hello() {}
	
	@GetMapping("/login")
	public void login() {}
	
	@GetMapping("/admin")
	public void getAdminPage() {}
	
	@GetMapping("/error")
	public String error() {
		return "redirect:/";
	}
	
}
