package com.fu.fuatsbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.CVScreening;

@Repository
public interface CVScreeningRepository extends JpaRepository<CVScreening, Integer> {

    CVScreening findById(int id);

    CVScreening findTopByOrderByIdDesc();

}
