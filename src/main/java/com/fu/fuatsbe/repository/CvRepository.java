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
                        + " order by id desc \n")
        // + " limit ?1 offset ?2")
        public List<CV> getCVs();

        @Query(nativeQuery = true, value = "select count(id) from cv \n"
                        + " where cv.id not in \n (select cv.id \n"
                        + "     from cv join job_apply on cv.id = job_apply.cv_id \n"
                        + "         join interview on interview.job_apply_id = job_apply.id \n"
                        + "         join interview_detail on interview.id = interview_detail.interview_id \n"
                        + "     where interview_detail.result like 'Pass' \n"
                        + " ) \n")
        public int getTotalCVs();

        @Query(nativeQuery = true, value = "Select distinct p.name \n" +
                        " from position p join cv_position cp on p.id = cp.position_id \n" +
                        " join cv c on c.id = cp.cv_id \n" +
                        " where c.id = ?1")
        public List<String> getPositionAppliedById(int id);

        @Query(nativeQuery = true, value = "select distinct c.* from cv c  \n" +
                        "       join cv_position cp on c.id = cp.cv_id  \n" +
                        "       join position p on p.id = cp.position_id  \n" +
                        "       where (c.recommend_positions in (select p.name from position p   \n" +
                        "	        join recruitment_request r on p.id = r.position_id)  \n" +
                        "       or p.id in (select p.id from position p   \n" +
                        "	        join recruitment_request r on p.id = r.position_id)  \n" +
                        "       )  \n" +
                        "       and c.id not in (select cv.id from cv   \n" +
                        "	        where cv.id in (select cv.id   \n" +
                        "	        from cv join job_apply on cv.id = job_apply.cv_id   \n" +
                        "		        join interview on interview.job_apply_id = job_apply.id   \n" +
                        "		        join interview_detail on interview.id = interview_detail.interview_id  \n" +
                        "	        where interview_detail.result like 'Pass')   \n" +
                        "       )  \n" +
                        "       and p.name like ?1")
        public List<CV> getCvForRequest(String positionName);

        @Query(nativeQuery = true,value = "select distinct c.email from cv cv join candidate c on c.id = cv.candidate_id where cv.id = ?1")
        String getEmailByCVId(int id);
}