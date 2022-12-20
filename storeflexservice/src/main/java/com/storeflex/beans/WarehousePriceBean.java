package com.storeflex.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class WarehousePriceBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UUID priceId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String availspace;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String ratesqtft;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String minordersqt;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String createBy;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime createDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String updatedBy;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime updateDate;
	
}
