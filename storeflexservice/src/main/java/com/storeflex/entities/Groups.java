package com.storeflex.entities;

import java.io.Serializable;

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
@Table(name="group")
public class Groups  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="group_id" ,nullable = false)
	private long grpId;
	@Column(name="group_type" ,nullable = false)
	private String grpType;
}
