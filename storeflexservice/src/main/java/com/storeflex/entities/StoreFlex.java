package com.storeflex.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="storeflex")
public class StoreFlex implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="storeflex_id" ,nullable = false)
	private String storeFlexId;
	@Column(name="aboutus" )
	private String aboutUs;
	@Column(name="compny_descp" )
	private String compyDesc;
	@Column(name="promotion" )
	private String promotion ;
	@Column(name="leaders")
	private String leaders;
	@Column(name="created_by" )
	private String createBy;
	@Column(name="created_date" )
	private LocalDateTime createDate;
	@Column(name="update_by" )
	private String updatedBy;
	@Column(name="update_date" )
	private LocalDateTime updateBy;
	@Column(name="status" )
	private boolean status;
		@OneToMany(mappedBy = "storeflex" ,cascade = CascadeType.ALL)
	private Set<StoreFlexAddress> storeFlexAddress =  new HashSet<StoreFlexAddress>();

	@OneToMany(mappedBy = "storeflex" ,cascade = CascadeType.ALL)
	private Set<StoreFlexContact> storeFlexContact =  new HashSet<StoreFlexContact>();

	@OneToMany(mappedBy = "storeflex" ,cascade = CascadeType.ALL)
	private Set<StoreFlexUsers> storeFlexUsers =  new HashSet<StoreFlexUsers>();
}
