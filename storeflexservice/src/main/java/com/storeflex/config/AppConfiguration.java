package com.storeflex.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@NoArgsConstructor
@Configuration
@Component
public class AppConfiguration {
	
	@Value("${testuser.username_temp}")
	private String test_user;
	@Value("${testuser.password_temp}")
	private String test_password;
	@Value("${testuser.username_SL}")
	private String sl_user;
	@Value("${testuser.password_SL}")
	private String sl_password;
	@Value("${testuser.username_CL}")
	private String cl_user;
	@Value("${testuser.password_CL}")
	private String cl_password;
	@Value("${testuser.username_CU}")
	private String cust_user;
	@Value("${testuser.password_CU}")
	private String cust_password;

}
