package com.storeflex.beans;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Component
public class LoginBean {

	private String redirect;
	
	private String username;
}
