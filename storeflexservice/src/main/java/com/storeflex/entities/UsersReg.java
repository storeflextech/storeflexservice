package com.storeflex.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="users_reg")
public class UsersReg  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="reg_id" ,nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID regId;
	
	@Column(name="email" ,nullable = false)
	private String email;
	
	@Column(name="pswd" ,nullable = false)
	private String pswd;
	
	@Column(name="ph_no" ,nullable = false)
	private String phno;
	
	@Column(name="status" ,nullable = false)
	private String status;
	
	@Column(name="created_by" )
	private String createBy;
	
	@Column(name="created_date" )
	private LocalDateTime createDate;
	
	@Column(name="update_by" )
	private String updatedBy;
	
	@Column(name="update_date" )
	private LocalDateTime updateBy;
	
	@Column(name="user_type" ,nullable = false)
	private String userType;
	
	@OneToOne(fetch = FetchType.LAZY,
	            cascade =  CascadeType.ALL,
	            mappedBy = "userReg")
	private StoreFlexUsers storeFlexUsers;
	
	
}
