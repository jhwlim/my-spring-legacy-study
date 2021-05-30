package com.spring.demo.domain.user.mapper;

import com.spring.demo.domain.user.model.User;

public interface UserMapper {

	public void createUser(User user);
	
	public User findUserByUsername(String username);
	
}
