package com.storeflex.entities;

import java.io.Serializable;
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
@Table(name="wareshouse_industry")
public class WarehouseIndustries implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id" ,nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "name_val", nullable = false)
	private String nameVal;
	@Column(name = "code" , nullable = false)
	private String code;
}
