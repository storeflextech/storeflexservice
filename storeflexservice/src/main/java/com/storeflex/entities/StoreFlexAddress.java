package com.storeflex.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="storeflex_address")
public class StoreFlexAddress  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="address_id" ,nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID addressId;
	@Column(name="address_type")
	private String addressType;
	@Column(name="house_no")
	private String houseNo;
	@Column(name="plot_no")
	private String plotNo;
	@Column(name="street_details")
	private String streetDetails;
	@Column(name="pincode")
	private String pincode;
	@Column(name="created_by")
	private String createBy;
	@Column(name="created_date")
	private LocalDateTime createDate;
	@Column(name="update_by")
	private String updateBy;
	@Column(name="update_date")
	private LocalDateTime updateDate;
	@Column(name="city_code")
	private String cityCode;
	@Column(name="state_name")
	private String stateCode;
	@Column(name="country_code")
	private String countryCode;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="storeflex_id")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private StoreFlex storeflex;
}
