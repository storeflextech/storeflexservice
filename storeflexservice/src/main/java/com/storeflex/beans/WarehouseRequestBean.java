package com.storeflex.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class WarehouseRequestBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String city;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String state;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String pincode;

}
