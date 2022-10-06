package com.storeflex.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="uniqueid")
public class UniqueId  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="prex_id")
	private String prex;
	
	@Column(name = "ctgry")
	private String ctgry;
	
	@Column(name = "next_rsrvd_id")
	private long nextReserveId;
	
	@Column(name = "created_by")
	private String createBy;
	
	@Column(name = "created_date")
	private LocalDateTime createDate;
	
	@Column(name = "update_by")
	private String updatedBy;
	
	@Column(name = "update_date")
	private LocalDateTime updateBy;

}
