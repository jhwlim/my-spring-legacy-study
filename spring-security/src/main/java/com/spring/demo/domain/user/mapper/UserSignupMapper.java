package com.spring.demo.domain.user.mapper;

import com.spring.demo.domain.user.dto.User;
import com.spring.demo.domain.user.dto.UserAuthority;

public interface UserSignupMapper {
	
	public void insertUser(User user);
	
	public void insertUserAuthority(UserAuthority userAuthority);
}
