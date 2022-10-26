package com.fu.fuatsbe.repository;

import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface InterviewRepository extends JpaRepository<Interview, Integer> {

    public List<Interview> findInterviewByCandidateId(int candidate_id);
}
