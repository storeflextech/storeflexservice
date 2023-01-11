package com.storeflex.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.storeflex.entities.UsersReg;

@Repository
public interface UserAuthRepository extends JpaRepository<UsersReg, UUID>{
   
	@Query("from UsersReg where email=:email")
	UsersReg searchEmailExist(String email);
	
	@Query("from UsersReg where email=:email and pswd=:pswd")
	UsersReg validateUser(String email,String pswd);

}
