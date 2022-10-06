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
public class StoreFlexClientContactBean implements Serializable{
	private static final long serialVersionUID = 1L;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UUID contactId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String contactName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String mobileNo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String landLine;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String landLineExt;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String emailId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String createBy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime createDate;
	private String updatedBy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime updateBy;
}
