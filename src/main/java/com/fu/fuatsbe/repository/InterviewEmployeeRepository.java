package com.fu.fuatsbe.repository;

import com.fu.fuatsbe.entity.InterviewEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface InterviewEmployeeRepository extends JpaRepository<InterviewEmployee, String> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from interview_employee where interview_id = ?1")
    void deleteInterviewEmployeeByInterviewId(int id);

    @Query(nativeQuery = true, value = "select * from  interview_employee where interview_id = ?1 and employee_id = ?2")
    InterviewEmployee findByInterviewAndEmployee(int idInterview, int idEmployee);

    @Query(nativeQuery = true, value = "select confirm_status from  interview_employee where interview_id = ?1 and employee_id = ?2")
    String findByInterviewAndEmployeeStatus(int idInterview, int idEmployee);

    @Query(nativeQuery = true, value = "select * from interview_employee where interview_id = ?1")
    List<InterviewEmployee> findByInterviewId(int id);

    @Query(nativeQuery = true, value = "select e.name, ie.confirm_status as status from employee e, interview_employee ie where ie.employee_id = e.id and ie.interview_id = ?1")
    List<Tuple> getNameAndConfirmStatusByInterviewId(int id);
}
