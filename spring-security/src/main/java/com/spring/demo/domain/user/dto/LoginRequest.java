package com.spring.demo.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginRequest {
	
	String username;
	String password;
	
	@Builder
	public LoginRequest() {}
	
}
