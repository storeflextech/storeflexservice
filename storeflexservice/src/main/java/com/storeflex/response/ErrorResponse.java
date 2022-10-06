package com.storeflex.response;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name="error")
@Setter
@Getter
public class ErrorResponse {
	
	public ErrorResponse(String statusCode,String status,
			String errorCode,Date timeStamp,String message,
			List<String> details) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.errorCode = errorCode;
		this.timestamp = timeStamp;
		this.message = message;
		this.details = details;
		
	}
	
	private String errorCode;
	private String message;
	private List<String> details;
	private Date timestamp;
	private String statusCode;
	private String status;
	

}
