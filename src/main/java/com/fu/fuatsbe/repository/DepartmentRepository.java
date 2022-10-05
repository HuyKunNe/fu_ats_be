package com.fu.fuatsbe.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Department;

@Repository
@Transactional
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    List<Department> findAll();

    List<Department> findByNameContaining(String name);

    boolean existsByName(String name);

    Optional<Department> findDepartmentByName(String name);
}
