package com.fu.fuatsbe.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.RecruitmentPlan;

@Repository
@Transactional
public interface RecruitmentPlanRepository extends JpaRepository<RecruitmentPlan, Integer> {

    Optional<RecruitmentPlan> findById(int id);

    Page<RecruitmentPlan> findByStatus(String status, Pageable pageable);

    Page<RecruitmentPlan> findByApprover(Employee employee, Pageable pageable);

    Page<RecruitmentPlan> findByCreator(Employee employee, Pageable pageable);
    @Query(nativeQuery = true,value = "select * from recruitment_plan where creator_id in" +
            "(select id from employee where department_id = ?1)")
    Page<RecruitmentPlan> findByDepartmentId(int departmentId, Pageable pageable);

}
