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
@Table(name = "client_contacts")
public class ClientContacts  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "contact_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID contactId;
	@Column(name = "contact_name")
	private String contactName;
	@Column(name = "mobile_no")
	private String mobileNo;
	@Column(name = "land_line")
	private String landLine;
	@Column(name = "land_line_ext")
	private String landLineExt;
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "created_by")
	private String createBy;
	@Column(name = "created_date")
	private LocalDateTime createDate;
	@Column(name = "update_by")
	private String updatedBy;
	@Column(name = "update_date")
	private LocalDateTime updateBy;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="client_id")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private ClientProfile clientProfile;
	
}
