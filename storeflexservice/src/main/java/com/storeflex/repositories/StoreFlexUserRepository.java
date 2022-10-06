package com.storeflex.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storeflex.entities.StoreFlexUsers;

public interface StoreFlexUserRepository extends JpaRepository<StoreFlexUsers, UUID>{

}
