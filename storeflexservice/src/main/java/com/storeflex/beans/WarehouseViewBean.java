package com.storeflex.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;

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
public class WarehouseViewBean implements Serializable{
	private static final long serialVersionUID = 9168270119817373255L;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String warehouseId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private String clientId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private String warehouseName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private String descp;
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createdBy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createdTime;
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean status;
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private String houseNo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private String plotNo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private String streetAddrs;
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private String city;
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private String state;
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private String pincode;
}
