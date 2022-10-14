package com.storeflex.beans;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class WarehouseViewBeanList implements Serializable{
	private static final long serialVersionUID = -57922037348780430L;
	private long totalRecord;
	private List<WarehouseViewBean> warehouseViewBean;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private ErrorCodeBean errorCode;
}
