package com.storeflex.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.storeflex.entities.ClientUsers;


@Repository
public interface ClientUsersRepository extends JpaRepository<ClientUsers, UUID>{

	@Query("from ClientUsers where email=:email")
	ClientUsers authorizeUser(String email);

	@Query("select h from ClientUsers h where h.status=:status and h.clientProfile.clientId=:clientId")
	List<ClientUsers> findByClient_Id(String clientId,String status,Pageable paging);
	@Query("select h from ClientUsers h where h.clientProfile.clientId=:clientId")
	List<ClientUsers> findByClient_Id(String clientId, Pageable paging);
}
