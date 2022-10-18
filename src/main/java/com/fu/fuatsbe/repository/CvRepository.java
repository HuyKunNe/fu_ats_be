package com.fu.fuatsbe.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.CV;
import com.fu.fuatsbe.entity.Candidate;

@Repository
@Transactional
public interface CvRepository extends JpaRepository<CV, Integer> {

    Page<CV> findByCandidate(Candidate candidate, Pageable pageable);

}
