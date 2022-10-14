package com.storeflex.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class ClientWareHouseHoursBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private String day;
	private String starttime;
	private String endtime;
	private boolean openall;
	private String createBy;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime createDate;
	private String updatedBy;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime updateDate;
	
}
