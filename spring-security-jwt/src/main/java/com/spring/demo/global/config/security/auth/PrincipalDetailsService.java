package com.spring.demo.global.config.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.demo.domain.user.mapper.UserMapper;
import com.spring.demo.domain.user.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	
	@Autowired
	UserMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = mapper.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username + " not exists");
		}
		return new PrincipalDetails(user);
	}
	
	
}
