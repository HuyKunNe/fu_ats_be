package com.fu.fuatsbe.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.City;

@Repository
@Transactional
public interface CityRepository extends JpaRepository<City, Integer> {

    Optional<City> findByName(String name);

}
