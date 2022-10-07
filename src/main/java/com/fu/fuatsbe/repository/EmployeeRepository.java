package com.fu.fuatsbe.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Employee;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Modifying
    @Query("select e from Employee e where department_id = ?1")
    List<Employee> findEmployeesByDepartmentId(int id);

    Optional<Employee> findById(int id);

    Optional<Employee> findByEmployeeCode(String employeeCode);

    Optional<Employee> findByPhone(String phone);

    @Modifying
    @Query(value = "select * from employee where position_id =?1 and status = ?2", nativeQuery = true)
    List<Employee> findbyPositionIdAndStatus(int id, String status);

}
