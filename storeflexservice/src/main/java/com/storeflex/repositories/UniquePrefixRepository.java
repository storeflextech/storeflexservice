package com.storeflex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.storeflex.entities.UniqueId;

@Repository
public interface UniquePrefixRepository extends JpaRepository<UniqueId, String>{

}
