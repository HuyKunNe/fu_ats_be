package com.fu.fuatsbe.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Department;

@Repository
@Transactional
public interface DepartmentRepository extends JpaRepository<Department, Integer>{
    
}
