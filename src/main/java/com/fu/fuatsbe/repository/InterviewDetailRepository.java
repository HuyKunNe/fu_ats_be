package com.fu.fuatsbe.repository;

import com.fu.fuatsbe.entity.InterviewDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface InterviewDetailRepository extends JpaRepository<InterviewDetail, Integer> {

    @Query(nativeQuery = true, value = "select * from interview_detail where interview_id = ?1")
    Optional<InterviewDetail> getInterviewDetailByInterviewId(int interviewId);
}
