package com.spring.demo.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {
	
	private int id;
	private String username;
	private String password;
	private boolean enabled;
	
	@Builder
	public User(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}
}
