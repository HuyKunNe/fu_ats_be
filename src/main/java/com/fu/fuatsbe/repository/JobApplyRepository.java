package com.fu.fuatsbe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.JobApply;
import com.fu.fuatsbe.entity.RecruitmentRequest;
import com.fu.fuatsbe.response.ReportDTO;

import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface JobApplyRepository extends JpaRepository<JobApply, Integer> {
        public Optional<JobApply> findById(int id);

        @Query(nativeQuery = true, value = "select * from job_apply where candidate_id = ?1 order by date desc")
        public Page<JobApply> findByCandidate(int candidateId, Pageable pageable);

        public Page<JobApply> findByStatus(String status, Pageable pageable);

        public Page<JobApply> findByRecruitmentRequest(RecruitmentRequest recruitmentRequest, Pageable pageable);

        @Query(nativeQuery = true, value = "select * from job_apply where id in" +
                        "(select id from recruitment_request where position_id in " +
                        "(select id from position where department_id = ?1)) and status like 'APPROVED'")
        Page<JobApply> getJobApplyByDepartment(int departmentId, Pageable pageable);

        @Query(nativeQuery = true, value = "(select * from job_apply where recruitment_request_id = ?1 and candidate_id = ?2 order by status) limit 1")
        Optional<JobApply> getJobAppliesByRecruitmentAndCandidate(int recruitmentId, int candidateId);

        Page<JobApply> findByRecruitmentRequestAndStatusNotLike(RecruitmentRequest request, String status,
                        Pageable pageable);

        public int countByRecruitmentRequestId(int id);

        public int countByRecruitmentRequestAndCandidate(RecruitmentRequest request, Candidate candidate);

        Page<JobApply> findByScreeningStatus(String screeningStatus, Pageable pageable);

        Page<JobApply> findByScreeningStatusAndRecruitmentRequest(String screeningStatus,
                        RecruitmentRequest recruitmentRequest, Pageable pageable);

        Page<JobApply> findByScreeningStatusLikeOrStatusLike(String screeningStatus, String status, Pageable pageable);

        @Query(nativeQuery = true, value = "select distinct rr.id as jobRequestId,rp.id as planId,pd.id as planDetailId ,d.id as departmentId, c.source as source, count(distinct(ja.id)) as totalCV, \n"
                        + "     (select (count(distinct(ja.id))) from job_apply ja \n"
                        + "             join cv on cv.id = ja.cv_id \n"
                        + "            where ja.screening_status like 'PASSED' \n"
                        + "             and ja.recruitment_request_id = rr.id \n"
                        + "             and cv.source = c.source \n"
                        + "     ) as totalAcceptableCV, \n"
                        + "     (select (count(distinct(ja.id))) from job_apply ja \n"
                        + "             join interview i on i.job_apply_id = ja.id \n"
                        + "             join cv on cv.id = ja.cv_id \n"
                        + "     where i.candidate_confirm like 'ACCEPTABLE' \n"
                        + "             and ja.recruitment_request_id = rr.id and cv.source = c.source \n"
                        + "     ) as totalJoinInterview, \n"
                        + "     (select (count(distinct(ja.id))) from job_apply ja \n"
                        + "             join interview i on i.job_apply_id = ja.id \n"
                        + "             join interview_detail ind on ind.interview_id = i.id \n"
                        + "             join cv on cv.id = ja.cv_id \n"
                        + "     where i.candidate_confirm like 'ACCEPTABLE' \n"
                        + "             and ja.recruitment_request_id = rr.id \n"
                        + "             and ind.result like 'PASSED' \n"
                        + "             and cv.source = c.source \n"
                        + "     ) as totalPassInterview \n"
                        + "from employee e join department d on e.department_id = d.id \n"
                        + "	join recruitment_plan rp on rp.creator_id = e.id \n"
                        + "     join plan_detail pd on rp.id = pd.recruitment_plan_id \n"
                        + "     join recruitment_request rr on rr.plan_detail_id = pd.id \n"
                        + "     join job_apply ja on ja.recruitment_request_id = rr.id \n"
                        + "     join cv c on c.id = ja.cv_id \n"
                        + "     join interview i on i.job_apply_id = ja.id \n"
                        + "     join interview_detail ind on i.id = ind.interview_id \n"
                        + "group by rp.name, d.name, rr.name, c.source \n"
                        + "order by rp.id desc")
        List<ReportDTO> getReport();

}