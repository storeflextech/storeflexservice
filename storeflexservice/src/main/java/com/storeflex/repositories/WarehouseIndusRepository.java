package com.storeflex.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.storeflex.entities.WarehouseIndustries;

@Repository
public interface WarehouseIndusRepository extends JpaRepository<WarehouseIndustries, UUID>{

}
