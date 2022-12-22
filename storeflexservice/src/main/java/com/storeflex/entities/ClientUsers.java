package com.storeflex.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "client_users")
public class ClientUsers  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name = "user_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID userId;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "middle_name")
	private String middleName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name="user_phto")
	private byte[] userPhoto;
	@Column(name="phto_name")
	private String photoName;
	
	@Column(name = "address")
	private String address;
	@Column(name = "house_no")
	private String houseNo;
	
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	
	@Column(name = "pincode")
	private String pincode;
	@Column(name = "mobile_no")
	private String mobileNo;
	@Column(name = "emergeny_phno")
	private String emergenyPhno;
	@Column(name = "email")
	private String email;
	@Column(name = "status")
	private String status;
	
	@Column(name="roletype")
	private String roleType;
	@Column(name="created_by" )
	private String createBy;
	@Column(name="created_date" )
	private LocalDateTime createDate;
	@Column(name="update_by" )
	private String updatedBy;
	@Column(name="update_date" )
	private LocalDateTime updateDate;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id")
	private UsersReg userReg;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="client_id")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private ClientProfile clientProfile;
}
