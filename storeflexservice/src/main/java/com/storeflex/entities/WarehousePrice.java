package com.storeflex.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Entity
@Table(name="warehouse_price")
public class WarehousePrice implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID priceId;
	@Column(name = "warehouse_id", nullable = false)
	private String warehouseId;
	@Column(name = "availspace", nullable = true)
	private String availspace;
	@Column(name = "ratesqtft", nullable = true)
	private String ratesqtft;
	@Column(name = "minordersqt", nullable = true)
	private String minordersqt;
	@Column(name = "created_by", nullable = true)
	private String createBy;
	@Column(name = "created_date", nullable = true)
	private LocalDateTime createDate;
	@Column(name = "update_by", nullable = true)
	private String updatedBy;
	@Column(name = "update_date", nullable = true)
	private LocalDateTime updateDate;
}
