package com.storeflex.beans;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ErrorCodeBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public String errorCode;
	
	public String errorMessage;
	
}

