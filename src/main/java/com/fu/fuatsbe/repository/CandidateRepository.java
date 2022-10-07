package com.fu.fuatsbe.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Candidate;

@Repository
@Transactional
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

    Optional<Candidate> findById(int id);

    List<Candidate> findByStatus(String status);

    Optional<Candidate> findByEmail(String email);

    Optional<Candidate> findByPhone(String phone);
}