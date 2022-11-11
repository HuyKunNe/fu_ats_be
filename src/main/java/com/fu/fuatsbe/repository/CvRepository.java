package com.fu.fuatsbe.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.CV;
import com.fu.fuatsbe.entity.Candidate;

@Repository
@Transactional
public interface CvRepository extends JpaRepository<CV, Integer> {

    Page<CV> findByCandidate(Candidate candidate, Pageable pageable);

    Optional<CV> findById(int id);

    @Query(nativeQuery = true, value = "select * from cv where id " +
            "in(select cv_id from job_apply where status like 'REJECTED')")
    List<CV> getRejectedCV();


}
