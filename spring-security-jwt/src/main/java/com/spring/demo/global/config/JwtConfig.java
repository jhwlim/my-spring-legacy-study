package com.spring.demo.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class JwtConfig {

	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.expirationTime}")
	private int expirationTime;
	
	@Value("${jwt.tokenPrefix}")
	private String tokenPrefix;
	
	@Value("${jwt.header}")
	private String headerName;
	
}
