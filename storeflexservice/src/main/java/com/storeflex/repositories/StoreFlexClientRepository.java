package com.storeflex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.storeflex.entities.ClientProfile;

@Repository
public interface StoreFlexClientRepository extends JpaRepository<ClientProfile, String>{

}
