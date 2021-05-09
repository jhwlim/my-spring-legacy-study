package com.spring.demo.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.demo.domain.user.dto.Account;

import lombok.extern.log4j.Log4j;

@RestController
@Log4j
public class UserRestController {
	
	@GetMapping("/api")
	public ResponseEntity<String> login(Account account) {
		log.info(account);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
}
