package com.spring.demo.global.config.security.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Log4j
public class MyFilter3 implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("필터3");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		req.setCharacterEncoding("UTF-8");
		
		// 토큰이 cos 라면 인증 진행, 아니라면 컨트롤러 진입을 제한한다.
		// → 아이디와 패스워드가 정상적으로 들어와서 로그인이 완료되면 토큰을 만들어주고 그걸 응답해준다.
		// 요청할 때 마다 header에 Authorization에 value 값으로 토큰을 가지고 온다.
		// 이때, 토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지만 검증하면 된다. (RSA or HS256)
		if (req.getMethod().equals("POST")) {
			log.info("POST 요청됨.");
			String headerAuth = req.getHeader("Authorization"); 
			log.info("headerAuth=" + headerAuth);
			
			if (headerAuth.equals("cos")) {
				chain.doFilter(request, response);
			} else {
				PrintWriter out = resp.getWriter();
				out.println("Not access!");
			}
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
