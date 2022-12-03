package com.storeflex.beans;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

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
public class StoreFlexUserBean implements Serializable{
	private static final long serialVersionUID = 1L;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UUID userId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String firstName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String middleName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String lastName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private  byte[] userPhoto;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String photoName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String mobileNo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String email;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String address;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String houseNo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String pincode;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String city;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String state;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String country;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String status;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String roleType;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String redirectUrl;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String loginType;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private StoreFlexBean storeFlex;
	

}
