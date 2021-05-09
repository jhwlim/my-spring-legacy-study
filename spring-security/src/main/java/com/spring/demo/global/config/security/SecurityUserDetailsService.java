package com.spring.demo.global.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.demo.domain.user.dto.Account;
import com.spring.demo.domain.user.mapper.UserFindMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class SecurityUserDetailsService implements UserDetailsService {

	@Autowired
	private UserFindMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("username=" + username);
		Account user = mapper.selectUserWithAuthorityByUsername(username);
		log.info("user=" + user);
		if (user == null) {
			throw new UsernameNotFoundException("Could not found user '" + username + "'");
		}
		
		return new SecurityUser(user);
	}
	
}
