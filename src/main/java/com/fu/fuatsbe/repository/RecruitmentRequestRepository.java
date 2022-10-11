package com.fu.fuatsbe.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.Position;
import com.fu.fuatsbe.entity.RecruitmentRequest;

@Repository
@Transactional
public interface RecruitmentRequestRepository extends JpaRepository<RecruitmentRequest, Integer> {

    Optional<RecruitmentRequest> findById(int id);

    Page<RecruitmentRequest> findByStatus(String status, Pageable pageable);

    Page<RecruitmentRequest> findByPosition(Position position, Pageable pageable);

    Page<RecruitmentRequest> findByCreator(Employee employee, Pageable pageable);

}
