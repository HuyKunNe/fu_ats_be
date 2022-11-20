package com.fu.fuatsbe.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Candidate;

@Repository
@Transactional
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

    Optional<Candidate> findById(int id);

    Page<Candidate> findByStatus(String status, Pageable pageable);

    Optional<Candidate> findByEmail(String email);

    Optional<Candidate> findByPhone(String phone);

    @Query(nativeQuery = true, value = "select distinct c.* from candidate c join job_apply ja on c.id = ja.candidate_id \n"
            + "where c.id in (select distinct candidate_id from job_apply where recruitment_request_id = ?1) \n"
            + "and ja.status like 'APPROVED' ")
    List<Candidate> getCandidateAppliedByRecruitment(int recruitmentId);
}