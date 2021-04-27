package com.spring.demo.global.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan("com.spring.demo.domain.**.mapper")
public class DatabaseConfig {
	
//	@Autowired
//	private ApplicationContext applicationContext;
	
	@Bean
	public HikariConfig hikariConfig() {
		return new HikariConfig("/config/dataSource.properties");
	}
	
	@Bean
	public HikariDataSource dataSource() {
		return new HikariDataSource(hikariConfig());
	}
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		// sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/config/mybatis-config.xml"));
		return sqlSessionFactoryBean;
	}
}
