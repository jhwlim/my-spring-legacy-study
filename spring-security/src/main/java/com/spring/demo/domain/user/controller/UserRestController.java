package com.spring.demo.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.demo.domain.user.dto.LoginRequest;

import lombok.extern.log4j.Log4j;

@RestController
@Log4j
public class UserRestController {
	
	@PostMapping("/api/post")
	public ResponseEntity<String> login(LoginRequest loginRequest) {
		log.info(loginRequest);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
}
