package com.storeflex.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;


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
public class StoreFlexBean implements Serializable{
	private static final long serialVersionUID = 1L;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String storeFlexId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String aboutUs;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String compyDesc;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String promotion ;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String leaders;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String createBy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime createDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String updatedBy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime updateDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private boolean status;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Set<StoreFlexAddressBean> storeFlexAddress;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Set<StoreFlexContactBean> storeFlexContact;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Set<StoreFlexUserBean> storeFlexUsers;
}
