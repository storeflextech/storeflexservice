package com.storeflex.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class WarehousehoursBean implements Serializable {

	private static final long serialVersionUID = -3542941652634964652L;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UUID id;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String warehouseId;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String openday;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String starttime;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String endtime;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private boolean openall;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String createBy;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime createDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String updatedBy;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime updateDate;

}
