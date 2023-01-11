package com.storeflex.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.storeflex.entities.GuestUsers;

@Repository
public interface GuestUsersRepository extends JpaRepository<GuestUsers, UUID> {
	
	@Query("from GuestUsers where email=:emailId")
	GuestUsers authorizeUser(String emailId);
	
}
