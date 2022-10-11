package com.fu.fuatsbe.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.PlanDetail;
import com.fu.fuatsbe.entity.RecruitmentPlan;

@Transactional
@Repository
public interface PlanDetailRepository extends JpaRepository<PlanDetail, Integer> {
    Optional<PlanDetail> findById(int id);

    Page<PlanDetail> findByStatus(String status, Pageable pageable);

    Page<PlanDetail> findByRecruitmentPlan(RecruitmentPlan recruitmentPlan, Pageable pageable);

    Page<PlanDetail> findByApprover(Employee employee, Pageable pageable);
}
