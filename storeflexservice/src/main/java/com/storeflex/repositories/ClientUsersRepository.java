package com.storeflex.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.storeflex.entities.ClientUsers;


@Repository
public interface ClientUsersRepository extends JpaRepository<ClientUsers, UUID>{

	@Query("from ClientUsers where email=:email")
	ClientUsers authorizeUser(String email);
}
