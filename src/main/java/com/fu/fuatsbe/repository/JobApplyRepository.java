package com.fu.fuatsbe.repository;

import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.JobApply;
import com.fu.fuatsbe.entity.RecruitmentRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobApplyRepository extends JpaRepository<JobApply, Integer> {
    public Optional<JobApply> findById(int id);

    public Page<JobApply> findByCandidate(Candidate candidate, Pageable pageable);

    public Page<JobApply> findByStatus(String status, Pageable pageable);

    public Page<JobApply> findByRecruitmentRequest(RecruitmentRequest recruitmentRequest, Pageable pageable);

}
