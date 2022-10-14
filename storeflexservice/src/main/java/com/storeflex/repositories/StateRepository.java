package com.storeflex.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.storeflex.entities.State;

@Repository
public interface StateRepository extends JpaRepository<State,Long>{
	@Query("from State where country.countryId=:countryId")
	List<State> getStateByCountryId(long countryId);
	@Query("select stateCode from State where stateName=:stateName")
	String getStateCode(String stateName);

}
