package com.storeflex.entities;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="state")
public class State  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="state_id" ,nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long stateId;
	@Column(name="state_name" ,nullable = false)
	private String stateName;
	@Column(name="state_code" ,nullable = false)
	private String stateCode;
	
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="country_id")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private Country country;
    
    @OneToMany(mappedBy = "state" ,cascade = CascadeType.ALL)
	private Set<City> city =  new HashSet<City>();
	
}
