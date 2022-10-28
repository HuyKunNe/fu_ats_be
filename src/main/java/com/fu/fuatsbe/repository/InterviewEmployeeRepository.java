package com.fu.fuatsbe.repository;

import com.fu.fuatsbe.entity.InterviewEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface InterviewEmployeeRepository extends JpaRepository<InterviewEmployee, String> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from interview_employee where interview_id = ?1")
    void deleteInterviewEmployeeByInterviewId(int id);
}
