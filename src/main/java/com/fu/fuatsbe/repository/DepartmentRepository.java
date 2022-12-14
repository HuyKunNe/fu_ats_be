package com.fu.fuatsbe.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Department;

@Repository
@Transactional
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Page<Department> findByNameContaining(String name, Pageable pageable);

    boolean existsByName(String name);

    @Query(nativeQuery = true, value = "select count(*) from department d \n" +
            " where d.name = ?1 and id != ?2")
    int totalDepartmentbyname(String name, int id);

    Optional<Department> findDepartmentByName(String name);

    @Query(nativeQuery = true, value = "select id, name from department where status like 'ACTIVATE'")
    List<Tuple> getIdAndNameDepartment();

    @Query(nativeQuery = true, value = "select * from department where status like 'ACTIVATE'")
    Page<Department> getAll(Pageable pageable);

    @Query(nativeQuery = true, value = "select count(id) as total from department")
    int countTotal();
}
