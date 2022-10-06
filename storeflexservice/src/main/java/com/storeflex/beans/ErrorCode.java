package com.storeflex.beans;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Component
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ErrorCode implements Serializable{

	private static final long serialVersionUID = 1L;
	
	String errorcode;
	
	String errorDesc;

}
