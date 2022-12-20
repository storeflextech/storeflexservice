package com.storeflex.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ClientWareHousesBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String warehouseId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String clientId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String warehouseName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String descp;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private byte[] profilePhoto;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String profilePhotoName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String warehouseTaxId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String createBy;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime createDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String updatedBy;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime updateDate;
	private boolean status;
	private String facilitiesId;
	private String industryId;
	private String storagesId;
	
	private String dockhighdoors;
	private String atgradedoors;
	private String ceillingheight;
	private String forkliftcapacity;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Set<ClientWareHouseAddrBean> address ;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Set<ClientWareHousePhtBean> photos;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private WarehousehoursBean hours;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private WarehousePriceBean warehouseprice;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private ErrorCode errorCode; 
}
