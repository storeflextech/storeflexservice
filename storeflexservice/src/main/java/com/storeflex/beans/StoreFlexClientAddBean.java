package com.storeflex.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class StoreFlexClientAddBean implements Serializable{
	private static final long serialVersionUID = 1L;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UUID addressId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String addressType;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String plotNo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String houseNo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String streetDetails;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String city;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String state;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String country;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String pincode;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String createBy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime createDate;
	private String updatedBy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime updateDate;
}
