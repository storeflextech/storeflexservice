package com.storeflex.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.storeflex.entities.WareHousePhoto;

@Repository
public interface WarehousePhotosRepository extends JpaRepository<WareHousePhoto, UUID>{
	@Query("from WareHousePhoto where warehouse.warehouseId=:warehouseId")
	List<WareHousePhoto> findByWarehouseId(String warehouseId);

}
