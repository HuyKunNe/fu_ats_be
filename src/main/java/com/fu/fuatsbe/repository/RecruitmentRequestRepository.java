package com.fu.fuatsbe.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.RecruitmentRequest;

@Repository
@Transactional
public interface RecruitmentRequestRepository extends JpaRepository<RecruitmentRequest, Integer> {

    Optional<RecruitmentRequest> findById(int id);

    List<RecruitmentRequest> findByStatus(String status);

    @Modifying
    @Query(value = "select * from recruitment_request r where r.creator_id = ?1", nativeQuery = true)
    List<RecruitmentRequest> findByCreatorId(int id);

}
