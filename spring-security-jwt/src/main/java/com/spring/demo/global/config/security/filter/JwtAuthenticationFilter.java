package com.spring.demo.global.config.security.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.demo.domain.user.model.User;
import com.spring.demo.global.config.JwtConfig;
import com.spring.demo.global.config.security.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

// 스프링 시큐리티의 UsernamePasswordAuthenticationFilter
// '/login' POST 요청할 때 username, password 전송하면 동작한다.
@Log4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
		this.authenticationManager = authenticationManager;
	}
	
	@Autowired
	private JwtConfig jwtConfig;
	
	// '/login' 요청을 하면 로그인 시도를 위해서 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		log.info("로그인 시도중");
		
		// 1. username, password를 받아서
		try {
//			BufferedReader br = request.getReader();
//			
//			String input = null;
//			while((input = br.readLine()) != null) {
//				log.info(input);
//			}
			
			ObjectMapper om = new ObjectMapper(); // json 데이터를 파싱한다.
			User user = om.readValue(request.getInputStream(), User.class);
			log.info(user);
			
			// 토큰 생성
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()); 
			
			// PrincipalDetailsService의 loadUserByUsername()이 실행된다.
			// authentication에 로그인 정보가 담긴다.
			// DB의 정보와 일치하면 authentication이 리턴된다.
			// 만약 패스워드가 일치하지 않는다면 401 에러가 발생한다.
			
			Authentication authentication = authenticationManager.authenticate(token);
			log.info(authentication);
			
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			log.info("로그인 완료됨 : " + principalDetails.getUser().getUsername());	// 로그인 정보
			
			// 인증이 완료되면 authentication 객체를 리턴한다. → session 영역에 저장해준다. 또한 궈한 관리를 security가 대신 해주기 때문에 편하려고 한다.
			// 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없다. 단지 권한 처리 때문에 session에 넣어준다.
			return authentication;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// attemptAuthentication()가 실행되고 인증이 정상적으로 되었으면 실행한다.
	// JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 된다.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		log.info("인증 완료");
		
		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
		
		// Hash 암호 방식
		// HMAC : 서버만 알고 있는 시크릿 키를 가지고 있다.
		String jwtToken = JWT.create()
				.withSubject("cos토큰")	// 토큰 이름?
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getExpirationTime())) // 토큰 만료일자 : 10분
				.withClaim("id", principalDetails.getUser().getId())
				.withClaim("username", principalDetails.getUser().getUsername())
				.sign(Algorithm.HMAC256(jwtConfig.getSecretKey()));
		
		response.addHeader(jwtConfig.getHeaderName(), jwtConfig.getTokenPrefix() + jwtToken); // 사용자에게 응답하는 헤더
		
		super.successfulAuthentication(request, response, chain, authResult);
	}
	
	
}
