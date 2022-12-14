package com.storeflex.beans;

import java.io.Serializable;
import java.util.Hashtable;

import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class WarehouseCategoriesBean implements Serializable{
 
	private static final long serialVersionUID = -2579977528973677257L;
	private String industry;
	private Hashtable<String,String> industries;
	private String storage;
	private Hashtable<String,String> storages;
	private String facility;
	private Hashtable<String,String> facilities;
}
