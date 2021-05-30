package com.spring.demo.global.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.spring.demo.global.config.security.auth.PrincipalDetailsService;
import com.spring.demo.global.config.security.filter.JwtAuthenticationFilter;
import com.spring.demo.global.config.security.filter.JwtAuthorizationFilter;
import com.spring.demo.global.config.security.filter.MyFilter3;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final CorsFilter corsFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailsService()).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {	
		// jwt 기본 설정
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않겠다.
			.and()
			.addFilter(corsFilter) 	// 인증이 있을 때는 필터에 등록해야 한다. cf> @CrossOrigin : 인증이 없을 때 사용
			.formLogin().disable()	// '/login' POST 요청을 처리하는 UsernamePasswordAuthenticationFilter 필터가 동작하지 않게된다. → 다시 등록해야한다.
			.addFilter(jwtAuthenticationFilter()) // AuthenticationManager를 매개변수로 전달해줘야 한다.
			.addFilter(jwtAuthorizationFilter())
			/*
			 * Http Basic 방식
			 * - Http Request Header에 Authorization에 아이디와 패스워드를 실어서 보낸다.
			 * - 암호화되지 않고 보내기 때문에 노출될 위험이 있다. → Https를 사용하면 암호화하여 보낼 수 있다.
			 * 
			 * Bearer 방식
			 * - Http Request Header에 토큰을 실어서 보낸다.
			 * - 노출되더라도 비교적 안전하다. (토큰은 유효시간이 있다.)
			 */
			.httpBasic().disable() // Http Basic 방식이 아니라 Bearer 방식을 적용
			.authorizeRequests()
				.antMatchers("/api/v1/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/api/v1/manager/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/api/v1/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll();
		
//		http.addFilter(new MyFilter1()); (x) 시큐리티 필터가 아니기 때문에 오류
//		http.addFilterBefore(new MyFilter3(), BasicAuthenticationFilter.class); // BasicAuthenticationFilter 필터가 실행되기 전에 해당 필터가 동작한다. 시큐리티 필터가 일반적으로 생성한 필터보다 우선적으로 실행된다.
		
	}
	
//	@Bean
//	@Override
//	public UserDetailsService userDetailsService() {
//		UserDetails user = User.withDefaultPasswordEncoder()
//					.username("user")	
//					.password("1234")	
//					.roles("USER")		
//					.build();
//		
//		return new InMemoryUserDetailsManager(user);
//	}
	
	@Bean
	public PrincipalDetailsService principalDetailsService() {
		return new PrincipalDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		return new JwtAuthenticationFilter(authenticationManager());
	}
	
	@Bean 
	public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
		return new JwtAuthorizationFilter(authenticationManager());
	}
}
