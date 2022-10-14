package com.storeflex.entities;

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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "warehouse_hours")
public class WarehouseHours {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "openday")
	private String day;
	@Column(name = "starttime")
	private String starttime;
	@Column(name = "endtime")
	private String endtime;
	@Column(name = "openall")
	private boolean openall;
	@Column(name = "created_by")
	private String createBy;
	@Column(name = "created_date")
	private LocalDateTime createDate;
	@Column(name = "update_by")
	private String updatedBy;
	@Column(name = "update_date")
	private LocalDateTime updateDate;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="warehouse_id")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
	private Warehouse warehouse;
}
