package com.storeflex.config;

import java.io.Serializable;

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
public class AppConfiguration implements Serializable{
	private static final long serialVersionUID = -2550185165626007488L;

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
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${spring.mail.host}")
	private String mailHost;
	@Value("${spring.mail.username}")
	private String mailHostUser;
	@Value("${spring.mail.password}")
	private String mailHostPsd;
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private boolean auth;
	@Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
	private long connectiontimeout;
	@Value("${spring.mail.properties.mail.smtp.timeout}")
	private long timeout;
	@Value("${spring.mail.properties.mail.smtp.writetimeout}")
	private long writetimeout;
	@Value("${spring.mail.properties.mail.starttls.enable}")
	private boolean enable;
	@Value("${spring.mail.username}")
	private String mailUser;
	@Value("${spring.mail.password}")
	private String mailUserPsw;
	
}
