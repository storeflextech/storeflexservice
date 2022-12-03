package com.storeflex.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class CustEnquiryBean implements Serializable{
	private static final long serialVersionUID = 1L;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UUID enquiryId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String firstName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String middleName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String lastName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String email;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String mobileNo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String subject;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String descp;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String createBy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime createDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String updateBy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime updateDate;
}
