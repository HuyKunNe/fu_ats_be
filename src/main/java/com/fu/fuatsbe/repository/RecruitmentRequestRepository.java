package com.fu.fuatsbe.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

        @Modifying
        @Query(value = "select distinct r.* from recruitment_request r join position p on r.position_id = p.id \n"
                        + " join recruitment_rquest_city rc on r.id = rc.request_id join city c on c.id = rc.city_id \n"
                        + "where concat(r.job_level,' ', p.name) like %?1% \n"
                        + "     and c.name like %?2% \n"
                        + "     and r.industry like %?3% \n"
                        + "     and r.job_level like %?4% \n"
                        + "     and r.type_of_work like %?5% \n"
                        + "     and ((case when  upper(?6) like '%TRÊN%' \n"
                        + "             then (cast(replace(upper(r.salary_from), upper('Trên'), ' ') as unsigned) >= cast(replace(upper(?6), 'TRÊN', ' ') as unsigned)) \n"
                        + "                     else cast(replace(upper(r.salary_from), 'TRÊN', ' ') as unsigned)  >= cast(?6 as unsigned) end) \n"
                        + "             and (case when ?7 like '' then true \n"
                        + "                     else cast(r.salary_to as unsigned)<= cast('' as unsigned)  \n"
                        + "         end)) \n"
                        + "      and (case \n"
                        + "             when ?8 like '' then cast(r.experience as signed)  >= 0 \n"
                        + "             when upper(?8) like '%TRÊN%' then replace(upper(r.experience), upper('Trên'), ' ') >= cast(replace(upper(?8), 'TRÊN', ' ') as unsigned) \n"
                        + "                     else cast(r.experience as unsigned) between cast(?8 as signed) -1 and cast(?8 as unsigned) +1 \n"
                        + "         end) \n"
                        + "     and r.status like 'OPENING' \n"
                        + "order by (case \n "
                        + "     when concat(r.job_level,' ', p.name) like %?1% then 2 \n"
                        + "     when c.name like %?2% then 1 \n"
                        + "     when r.industry like %?3% then 3 \n"
                        + "     when r.job_level like %?4% then 4 \n"
                        + "     when r.type_of_work like %?5% then 5 \n"
                        + "     when ((case when  upper(?6) like '%TRÊN%' \n"
                        + "             then (cast(replace(upper(r.salary_from), upper('Trên'), ' ') as unsigned) >= cast(replace(upper(?6), 'TRÊN', ' ') as unsigned)) \n"
                        + "                     else cast(replace(upper(r.salary_from), 'TRÊN', ' ') as unsigned)  >= cast(?6 as unsigned) end) \n"
                        + "             and (case when ?7 like '' then true \n"
                        + "                     else cast(r.salary_to as unsigned)<= cast('' as unsigned)  \n"
                        + "         end)) then 6 \n"
                        + "     when (case \n"
                        + "             when ?8 like '' then cast(r.experience as signed)  >= 0 \n"
                        + "             when upper(?8) like '%TRÊN%' then replace(upper(r.experience), upper('Trên'), ' ') >= cast(replace(upper(?8), 'TRÊN', ' ') as unsigned) \n"
                        + "                     else cast(r.experience as unsigned) between cast(?8 as signed) -1 and cast(?8 as unsigned) +1 \n"
                        + "         end) then 7\n"
                        + "end);", nativeQuery = true)
        List<RecruitmentRequest> searchRecruitmentRequest(String jobName, String province, String industry,
                        String jobLevel,
                        String typeOfWork, String salaryFrom, String salaryTo, String experience);

        @Modifying
        @Query(value = "SELECT DISTINCT p.name \n" +
                        "FROM recruitment_request r, position p \n" +
                        "where r.position_id = p.id", nativeQuery = true)
        List<String> getDistinctByPosition();

        @Modifying
        @Query(value = "select distinct industry from recruitment_request", nativeQuery = true)
        List<String> getDistinctByIndustry();

        Page<RecruitmentRequest> findByOrderByIdDesc(Pageable pageable);

}
