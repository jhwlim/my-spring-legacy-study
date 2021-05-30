package com.spring.demo.domain.user.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
public class User {
	
	private int id;
	private String username;
	private String password;
	private String roles;	// USER, ADMIN
	
	
	public List<String> getRoleList() {
		if (this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));
		}
		return new ArrayList<>();
	}
	
}
