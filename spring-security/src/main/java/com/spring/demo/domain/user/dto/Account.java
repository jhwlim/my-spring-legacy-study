package com.spring.demo.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Account {
	
	private int id;
	private String username;
	private String password;
	private boolean enabled;
	
	private String image;
	private String authority;
	
		
	@Builder
	public Account(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}
	
	@Builder
	public Account() {}
}
