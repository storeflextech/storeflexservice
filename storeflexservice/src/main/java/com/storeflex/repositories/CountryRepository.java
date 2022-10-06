package com.storeflex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storeflex.entities.Country;
public interface CountryRepository extends JpaRepository<Country,Long>{ 

}
