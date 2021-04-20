package com.spring.demo.global.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import jdk.internal.jline.internal.Log;
import lombok.extern.log4j.Log4j;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication()
//			.dataSource(dataSource)
//			.passwordEncoder(passwordEncoder())
//			.usersByUsernameQuery(
//					"SELECT username, password, enabled FROM users WHERE username = ?")
//			.authoritiesByUsernameQuery(
//					"SELECT u.username, a.authority " +
//					"FROM user_authorities a, users u " +
//					"WHERE u.username = ? AND u.id = a.user_id"
//					);
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//		super.configure(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();
		
		http.authorizeRequests()
			.antMatchers("/", "/home").permitAll()
			.antMatchers("/admin").hasAuthority("ROLE_ADMIN");
//			.antMatchers("/admin").hasRole("ADMIN")
//			.anyRequest().authenticated();
		
		http.formLogin()
			.loginPage("/login")
			.permitAll();
				
		http.logout()
			.permitAll();
	
		// http.exceptionHandling().accessDeniedPage("/error");
		
		super.configure(http);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
