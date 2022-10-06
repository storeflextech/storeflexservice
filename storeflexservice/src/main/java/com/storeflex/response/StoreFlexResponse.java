package com.storeflex.response;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Setter
@Getter
@ToString
public class StoreFlexResponse<T>{

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T methodReturnValue;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer statusCode;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private ErrorResponse errorResponse;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Status status;
	
	public enum Status{
		SUCCESS(600,"STATUS"),
		USER_INPUT(602,"USER INPUT ERROR"),
		BUSENESS_ERROR(603,"BUSINESS_ERROR"),
		NETWORK_ISSUE(604,"CALLING SERVICE DOWN");
		
		Status(int code, String name){
			this.code=code;
			this.name=name;
		}
		
		private int code;
		private String name ;
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		
		
		
	}
}
