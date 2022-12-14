package com.storeflex.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="warehouse")
public class Warehouse  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "warehouse_id", nullable = false)
	private String warehouseId;
	@Column(name = "client_id", nullable = false)
	private String clientId;
	@Column(name = "warehouse_name")
	private String warehouseName;
	@Column(name = "warehouse_descp")
	private String descp;
	@Column(name = "warehouse_profile_photo")
	private byte[] profilePhoto;
	
	@Column(name = "warehouse_profile_photo_name")
	private String profilePhotoName;
	@Column(name = "warehouse_tax_id")
	private String warehouseTaxId;
	
	@Column(name = "created_by")
	private String createBy;
	@Column(name = "created_date")
	private LocalDateTime createDate;
	@Column(name = "update_by")
	private String updatedBy;
	@Column(name = "update_date")
	private LocalDateTime updateDate;
	@Column(name = "status")
	private String status;
	@Column(name = "warehouse_facility_ids")
	private String facilitiesId;
	@Column(name = "warehouse_industry_ids")
	private String industryId;
	@Column(name = "warehouse_storage_ids")
	private String storagesId;
	@Column(name = "dockhighdoors")
	private String dockhighdoors;
	@Column(name = "atgradedoors")
	private String atgradedoors;
	@Column(name = "ceillingheight")
	private String ceillingheight;
	@Column(name = "forkliftcapacity")
	private String forkliftcapacity;
	
	@OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
	private Set<WarehouseAddress> address =  new HashSet<WarehouseAddress>();

	@OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
	private Set<WareHousePhoto> photos =  new HashSet<WareHousePhoto>();

 }
