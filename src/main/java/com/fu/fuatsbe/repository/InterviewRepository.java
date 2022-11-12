package com.fu.fuatsbe.repository;

import com.fu.fuatsbe.entity.Interview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface InterviewRepository extends JpaRepository<Interview, Integer> {

    Page<Interview> findInterviewByCandidateId(int candidate_id, Pageable pageable);

    @Query(nativeQuery = true, value = "select distinct * from interview where id in \n" +
            "(select interview_id from interview_employee where employee_id = ?1)")
    Page<Interview> findInterviewByEmployeeId(int employee_id, Pageable pageable);

}
