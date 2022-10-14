package com.storeflex.view.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.storeflex.view.entities.WarehouseView;

@Repository
public interface WarehouseViewRepository extends PagingAndSortingRepository<WarehouseView, String>,JpaSpecificationExecutor{

}
