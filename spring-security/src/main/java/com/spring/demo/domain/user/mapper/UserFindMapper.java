package com.spring.demo.domain.user.mapper;

import com.spring.demo.domain.user.dto.Account;

public interface UserFindMapper {
	
	public Account selectUserWithAuthorityByUsername(String username);
	
}
