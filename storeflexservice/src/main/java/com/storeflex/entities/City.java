package com.storeflex.entities;

import java.io.Serializable;
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
@Table(name="city")
public class City  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="city_id" ,nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long cityId;
	@Column(name="city_name" ,nullable = false)
	private String cityName;
	@Column(name="city_code" ,nullable = false)
	private String cityCode;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="state_id")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private State state;
}
