package com.fu.fuatsbe.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.RecruitmentPlan;

@Repository
@Transactional
public interface RecruitmentPlanRepository extends JpaRepository<RecruitmentPlan, Integer> {

    Optional<RecruitmentPlan> findById(int id);

    List<RecruitmentPlan> findByStatus(String status);

    @Modifying
    @Query(value = "select * from recruitment_plan r where r.approver_id = ?1", nativeQuery = true)
    List<RecruitmentPlan> findByApproverId(int id);

    @Modifying
    @Query(value = "select * from recruitment_plan r where r.creator_id = ?1", nativeQuery = true)
    List<RecruitmentPlan> findByCreatorId(int id);

}
