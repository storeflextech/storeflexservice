package com.storeflex.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.storeflex.entities.WarehouseAddress;

@Repository
public interface WarehouseAddressRepository extends JpaRepository<WarehouseAddress, UUID>{
	@Query("from WarehouseAddress where cityId=:cityCode")
	List<WarehouseAddress> getWarehouseByCity(String cityCode);

}
