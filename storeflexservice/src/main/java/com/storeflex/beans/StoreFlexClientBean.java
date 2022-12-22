package com.storeflex.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.storeflex.entities.ClientAddress;
import com.storeflex.entities.ClientContacts;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class StoreFlexClientBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String clientId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String compyName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String compyDesc;
	@JsonInclude(JsonInclude.Include.NON_NULL) 
	private byte[] photo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String photoName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String photoType;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String url;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String gstNo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String createBy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime createDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String updatedBy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime updatedate;
	private boolean status = false;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Set<StoreFlexClientAddBean> addresses;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Set<StoreFlexClientContactBean> contact ;
	
	
}
