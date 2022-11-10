package com.fu.fuatsbe.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.City;

@Repository
@Transactional
public interface CityRepository extends JpaRepository<City, Integer> {

    Optional<City> findByName(String name);

    @Query(nativeQuery = true, value = "select name from city")
    List<String> getAllCityName();

}
