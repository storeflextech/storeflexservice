package com.storeflex.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storeflex.entities.ClientAddress;

public interface StoreFlexClientAddrsRepository extends JpaRepository<ClientAddress, UUID>{

}
