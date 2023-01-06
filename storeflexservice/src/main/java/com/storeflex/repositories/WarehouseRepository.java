package com.storeflex.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.storeflex.entities.Warehouse;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, String>{

	@Query(value="select w from Warehouse w where w.clientId=:clientId",nativeQuery = true)
	Page<Warehouse> getWarehouseListByClient(String clientId, Pageable paging);
	
	Page<Warehouse> findByClientId(String clientId, Pageable paging);
	
	@Query("from Warehouse where warehouseTaxId =:gst")
	Optional<Warehouse> findByGst(String gst);


}
