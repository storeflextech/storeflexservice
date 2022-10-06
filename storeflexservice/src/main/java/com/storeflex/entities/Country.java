package com.storeflex.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="country")
public class Country  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="country_id" ,nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long countryId;
	@Column(name="country_name" ,nullable = false)
	private String countryName;
	@Column(name="country_code" ,nullable = false)
	private String countryCode;
	
	@OneToMany(mappedBy = "country" ,cascade = CascadeType.ALL)
	private Set<State> state =  new HashSet<State>();
	
}
