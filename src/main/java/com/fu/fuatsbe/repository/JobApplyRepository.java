package com.fu.fuatsbe.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fu.fuatsbe.entity.JobApply;
import com.fu.fuatsbe.entity.RecruitmentRequest;

public interface JobApplyRepository extends JpaRepository<JobApply, Integer> {
    public Optional<JobApply> findById(int id);

    @Query(nativeQuery = true, value = "select * from job_apply where candidate_id = ?1 order by date desc")
    public Page<JobApply> findByCandidate(int candidateId, Pageable pageable);

    public Page<JobApply> findByStatus(String status, Pageable pageable);

    public Page<JobApply> findByRecruitmentRequest(RecruitmentRequest recruitmentRequest, Pageable pageable);

}
