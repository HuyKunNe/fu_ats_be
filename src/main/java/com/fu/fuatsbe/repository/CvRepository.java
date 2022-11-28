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

    @Transactional
    @Query(nativeQuery = true, value = "select cv.* from cv \n"
            + " where cv.id not in \n (select cv.id \n"
            + "     from cv join job_apply on cv.id = job_apply.cv_id \n"
            + "         join interview on interview.job_apply_id = job_apply.id \n"
            + "         join interview_detail on interview.id = interview_detail.interview_id \n"
            + "     where interview_detail.result like 'Pass' \n"
            + " ) \n"
            + " order by id desc \n"
            + " limit ?1 offset ?2")
    public List<CV> getCVs(int pageSize, int pageNo);

    @Query(nativeQuery = true, value = "select count(id) from cv \n"
            + " where cv.id not in \n (select cv.id \n"
            + "     from cv join job_apply on cv.id = job_apply.cv_id \n"
            + "         join interview on interview.job_apply_id = job_apply.id \n"
            + "         join interview_detail on interview.id = interview_detail.interview_id \n"
            + "     where interview_detail.result like 'Pass' \n"
            + " ) \n")
    public int getTotalCVs();
}