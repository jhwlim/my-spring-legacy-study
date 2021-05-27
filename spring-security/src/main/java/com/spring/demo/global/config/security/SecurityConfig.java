package com.spring.demo.global.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.spring.demo.global.config.security.filter.RestAuthenticationFilter;
import com.spring.demo.global.config.security.handler.RestAuthenticationFailureHandler;
import com.spring.demo.global.config.security.handler.RestAuthenticationSuccessHandler;
import com.spring.demo.global.config.security.handler.RestLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {			
		http.authorizeRequests()
			.antMatchers("/", "/home").permitAll()
			.antMatchers("/api/**").permitAll()
			.anyRequest().authenticated();
		
		http.formLogin()
			.loginPage("/login")
			.permitAll();
				
		http.logout()
			.logoutSuccessHandler(logoutSuccessHandler())
			.permitAll();
		
		http.addFilterAt(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);	// UsernamePasswordAuthenticationFilter 자리에 생성한 필터 삽입
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public RestAuthenticationFilter restAuthenticationFilter() throws Exception {
		RestAuthenticationFilter filter = new RestAuthenticationFilter(new AntPathRequestMatcher("/login", "POST")); // URL, HttpMethod
		filter.setAuthenticationManager(this.authenticationManager());
		filter.setAuthenticationSuccessHandler(new RestAuthenticationSuccessHandler());
		filter.setAuthenticationFailureHandler(new RestAuthenticationFailureHandler());
		return filter;
	}
	
	@Bean
	public RestLogoutSuccessHandler logoutSuccessHandler() {
		return new RestLogoutSuccessHandler();
	}
}
