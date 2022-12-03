package com.storeflex.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cust_enquiry")
public class CustEnquiry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "enquiry_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID enquiryId;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "middle_name")
	private String middleName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "email_id")
	private String email;
	@Column(name = "mobile_no")
	private String mobileNo;
	@Column(name = "subject")
	private String subject;
	@Column(name = "descp")
	private String descp;
	@Column(name="created_by" )
	private String createBy;
	@Column(name="created_date" )
	private LocalDateTime createDate;
	@Column(name="update_by" )
	private String updatedBy;
	@Column(name="update_date" )
	private LocalDateTime updateBy;
}
