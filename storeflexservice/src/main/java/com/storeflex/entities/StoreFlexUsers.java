package com.storeflex.entities;

import java.io.Serializable;
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
@Table(name="storeflex_users")
public class StoreFlexUsers  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="users_id" ,nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID userId;	
	@Column(name="pwsd")
	private String pwd;
	@Column(name="first_name")
	private String firstName;
	@Column(name="middle_name")
	private String middleName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="user_phto")
	private byte[] userPhoto;
	@Column(name="phto_name")
	private String photoName;
	@Column(name="mobile_no")
	private String mobileNo;
	@Column(name="email")
	private String email;
	@Column(name="address")
	private String address ;
	@Column(name="house_no")
	private String houseNo;
	@Column(name="pin_code")
	private String pinCode;
	@Column(name="city")
	private String city;
	@Column(name="state")
	private String state;
	@Column(name="country")
	private String country;
	@Column(name="status")
	private String status;
	@Column(name="roletype")
	private String roleType;
	
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="storeflex_id")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private StoreFlex storeflex;

}
