package com.spring.demo.global.config.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.spring.demo.domain.user.dto.Account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;


@Getter
@ToString
@Log4j
public class SecurityUser extends User {

	private Account account;
	
	public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public SecurityUser(Account user) {
		super(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getAuthority())));
		this.account = user;
	}
	
	public Account getAccount() {
		return account;
	}
	
	
}
