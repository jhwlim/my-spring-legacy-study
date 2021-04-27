package com.spring.demo.domain.user.mapper;

import com.spring.demo.domain.user.dto.Account;
import com.spring.demo.domain.user.dto.UserAuthority;

public interface UserSignupMapper {
	
	public void insertUser(Account user);
	
	public void insertUserAuthority(UserAuthority userAuthority);
	
}
