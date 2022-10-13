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
	
	@Value("${testuser.username}")
	private String testuser;
	@Value("${testuser.password}")
	private String password;

}
