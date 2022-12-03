package com.storeflex.beans;

import java.io.Serializable;

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
public class TestAuthBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private String emailId;
	
	private String password;

}
