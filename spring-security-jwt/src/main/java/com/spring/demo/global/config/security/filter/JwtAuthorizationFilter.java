package com.spring.demo.global.config.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.spring.demo.domain.user.mapper.UserMapper;
import com.spring.demo.domain.user.model.User;
import com.spring.demo.global.config.JwtConfig;
import com.spring.demo.global.config.security.auth.PrincipalDetails;

import lombok.extern.log4j.Log4j;

// 시큐리티가 filter를 가지고 있는데 그 필터 중 BasicAuthenticationFilter 라는 것이 있다.
// 권한이나 인증이 필요한 특정 주소를 요청했을 때, 위의 필터를 무조건 타게 되어 있다.
// 만약에 권한이 인증이 필요한 주소가 아니라면 이 필터를 타지 않는다.
@Log4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	@Autowired
	private UserMapper mapper;

	@Autowired
	private JwtConfig jwtConfig;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	/*
	 * 인증이나 권한이 필요한 주소 요청이 있을 때 해당 필터를 타게 된다.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		super.doFilterInternal(request, response, chain);
		log.info("인증이나 권한이 필요한 요청");
	
		String jwtHeader = request.getHeader(jwtConfig.getHeaderName());
		log.info("jwtHeader=" + jwtHeader);
		
		// header가 있는지 확인하기
		if (jwtHeader == null || !jwtHeader.startsWith(jwtConfig.getTokenPrefix())) {
			chain.doFilter(request, response);
			return;
		}
		
		// JWT 토큰을 검증해서 정상적인 사용자인지 확인
		String jwtToken = jwtHeader.replace(jwtConfig.getTokenPrefix(), ""); // Bearer을 공백으로 치환한다.
	
		String username = JWT.require(Algorithm.HMAC256(jwtConfig.getSecretKey())).build().verify(jwtToken).getClaim("username").asString();
		log.info("username=" + username);
		// 서명이 정상적으로 되었다면
		if (username != null) {
			User user = mapper.findUserByUsername(username);
			
			PrincipalDetails principalDetails = new PrincipalDetails(user);
		
			// Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 생성한다.
			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
			
			// 강제로 시큐리티 세션에 접근하여 Authentication 객체를 저장한다.
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}

		chain.doFilter(request, response);
	}
	
}
