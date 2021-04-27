package com.spring.demo.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserAuthority {
	
	private int id;
	private int userId;
	private String authority;
	
	@Builder
	public UserAuthority(int userId, String authority) {
		this.userId = userId;
		this.authority = authority;
	}
	
}
