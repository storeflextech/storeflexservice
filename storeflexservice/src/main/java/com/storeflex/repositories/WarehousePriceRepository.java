package com.storeflex.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.storeflex.entities.WarehousePrice;


@Repository
public interface WarehousePriceRepository extends JpaRepository<WarehousePrice, UUID>{

	@Query("from WarehousePrice where warehouseId=:warehouseId")
	Optional<WarehousePrice> getPriceForWarehouse(String warehouseId);

}
