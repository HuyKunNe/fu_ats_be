package com.fu.fuatsbe.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Department;

@Repository
@Transactional
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    List<Department> findAll();

    List<Department> findByNameContaining(String name);

    @Modifying
    @Query("update Department set status = 1 where id=?1")
    int deleteDepartmentById(int id);

    boolean existsByName(String name);
}
