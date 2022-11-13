package com.fu.fuatsbe.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Interview;

@Repository
@Transactional
public interface InterviewRepository extends JpaRepository<Interview, Integer> {

        Page<Interview> findInterviewByCandidateId(int candidate_id, Pageable pageable);

        @Query(nativeQuery = true, value = "select distinct * from interview where id in \n" +
                        "(select interview_id from interview_employee where employee_id = ?1)")
        Page<Interview> findInterviewByEmployeeId(int employee_id, Pageable pageable);

        // @Query(nativeQuery = true, value = "select distinct inter.* from interview
        // inter \n"
        // + " join interview_employee ie on inter.id = ie.interview_id \n"
        // + " join employee e on ie.employee_id = e.id \n"
        // + " where e.department_id = ?1")
        // Page<Interview> findInterviewByDepartrment(int department_id, Pageable
        // pageable);

        @Query(nativeQuery = true, value = "select * from interview \n"
                        + "where id in (select interview_id from interview_employee \n"
                        + "where employee_id in (select id from employee where department_id = ?1))")
        Page<Interview> findInterviewByDepartrment(int department_id, Pageable pageable);

}
