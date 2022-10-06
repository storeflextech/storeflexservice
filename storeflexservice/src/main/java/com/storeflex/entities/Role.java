package com.storeflex.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="role")
public class Role  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="role_id" ,nullable = false)
	private long roleId;
	@Column(name="role_type" ,nullable = false)
	private String roleType;
}
