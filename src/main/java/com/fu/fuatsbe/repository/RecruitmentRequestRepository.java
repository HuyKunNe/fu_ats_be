package com.fu.fuatsbe.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        @Query(value = "select r.* from recruitment_request r join position p on r.position_id = p.id \n"
                        + "where concat(r.job_level,' ', p.name) like %?1% \n"
                        + "     and r.industry like %?2% \n"
                        + "     and r.job_level like %?3% \n"
                        + "     and r.type_of_work like %?4% \n"
                        + "     and cast(r.salary as unsigned) >= cast(?5 as unsigned) \n"
                        + "     and r.experience like %?6% \n"
                        + "     and r.status like 'OPENING' \n"
                        + "order by (case \n "
                        + "     when concat(r.job_level,' ', p.name) like %?1% then 1 "
                        + "     when r.industry like %?2% then 2 \n"
                        + "     when r.job_level like %?3% then 3"
                        + "     when r.type_of_work like %?4% then 4 \n"
                        + "     when cast(r.salary as unsigned) >= cast(?5 as unsigned) then 5 \n"
                        + "     when r.experience like %?6% then 6 \n"
                        + "end);", nativeQuery = true)
        List<RecruitmentRequest> searchRecruitmentRequest(String jobName, String industry, String jobLevel,
                        String typeOfWork, String salary, String experience);

        @Modifying
        @Query(value = "SELECT DISTINCT p.name \n" +
                        "FROM recruitment_request r, position p \n" +
                        "where r.position_id = p.id", nativeQuery = true)
        List<String> getDistinctByPosition();

        @Modifying
        @Query(value = "select distinct industry from recruitment_request", nativeQuery = true)
        List<String> getDistinctByIndustry();
}
