package com.storeflex.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.storeflex.entities.City;
public interface CityRepository extends JpaRepository<City,Long>{
	@Query("from City where state.stateCode=:stateCode")
	List<City> getCityByStateId(String stateCode);

	@Query("select cityCode from City where cityName=:cityName")
	String getCityCode(String cityName);
}
