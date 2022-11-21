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

        @Query(nativeQuery = true, value = "select * from job_apply where id in" +
                        "(select id from recruitment_request where position_id in " +
                        "(select id from position where department_id = ?1)) and status like 'APPROVED'")
        Page<JobApply> getJobApplyByDepartment(int departmentId, Pageable pageable);

        @Query(nativeQuery = true, value = "(select * from job_apply where recruitment_request_id = ?1 and candidate_id = ?2 order by status) limit 1")
        Optional<JobApply> getJobAppliesByRecruitmentAndCandidate(int recruitmentId, int candidateId);

        Page<JobApply> findByRecruitmentRequestAndStatusNotLike(RecruitmentRequest request, String status,
                        Pageable pageable);

        public int countByRecruitmentRequestId(int id);

}
