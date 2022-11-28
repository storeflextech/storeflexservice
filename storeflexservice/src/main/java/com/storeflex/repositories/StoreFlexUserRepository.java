package com.storeflex.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.storeflex.entities.StoreFlexUsers;

@Repository
public interface StoreFlexUserRepository extends JpaRepository<StoreFlexUsers, UUID>{
	@Query("from StoreFlexUsers where email=:email")
	StoreFlexUsers authorizeUser(String email);

	
}
