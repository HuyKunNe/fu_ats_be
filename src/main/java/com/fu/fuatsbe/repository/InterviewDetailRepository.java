package com.fu.fuatsbe.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.InterviewDetail;

@Repository
@Transactional
public interface InterviewDetailRepository extends JpaRepository<InterviewDetail, Integer> {

    @Query(nativeQuery = true, value = "select * from interview_detail where interview_id = ?1")
    Optional<InterviewDetail> getInterviewDetailByInterviewId(int interviewId);

    @Query(nativeQuery = true, value = "SELECT distinct ind.* FROM fu_ats_db.interview_detail ind \n" +
            "join interview i on ind.interview_id = i.id \n" +
            "join interview_employee ie on i.id = ie.interview_id \n" +
            "join employee e on ie.employee_id = e.id \n" +
            " join department d on e.department_id = d.id \n" +
            "where d.name like %?1% \n"
            + "order by ind.id desc")
    Page<InterviewDetail> getInterviewDetailByDepartment(String departmentName, Pageable pageable);
    boolean existsByInterviewId(int interviewId);
}
