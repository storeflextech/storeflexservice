package com.storeflex.view.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="warehouseview")
public class WarehouseView implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "warehouse_id")
    private String warehouseId;
	@Column(name = "client_id")
    private String clientId;
	@Column(name = "warehouse_name")
    private String warehouseName;
	@Column(name = "warehouse_descp")
    private String descp;
	@Column(name = "created_by")
    private String createdBy;
	@Column(name = "created_date")
    private LocalDateTime createdTime;
	@Column(name = "status")
    private boolean status;
	@Column(name = "house_no")
    private String houseNo;
	@Column(name = "plot_no")
    private String plotNo;
	@Column(name = "street_details")
    private String streetAddrs;
	@Column(name = "city")
    private String city;
	@Column(name = "state")
    private String state;
	@Column(name = "pincode")
    private String pincode;
    
    
}
