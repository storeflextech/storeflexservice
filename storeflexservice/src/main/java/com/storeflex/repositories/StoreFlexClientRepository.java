package com.storeflex.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.storeflex.entities.ClientProfile;

@Repository
public interface StoreFlexClientRepository extends JpaRepository<ClientProfile, String>,PagingAndSortingRepository<ClientProfile, String> ,JpaSpecificationExecutor<ClientProfile>{
	@Query("from ClientProfile where status =:status")
	List<ClientProfile> getActiveCompany(String status);

}
