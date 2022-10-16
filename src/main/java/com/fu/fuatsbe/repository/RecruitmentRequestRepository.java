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

        // @Modifying
        // @Query(value = "SELECT r from RecruitmentRequest r join Position p on
        // r.positionId = p.id \n"
        // + "where concat(r.job_level, ', p.name) like N'%?1%' \n"
        // + "or r.industry '%?2%' \n"
        // + "or r.jobLevel like N'%?3%' \n"
        // + "or r.typeOfWork like N'%?4%' \n"
        // + "or salary >= N'%?5%' \n"
        // + "or r.experience >= N'%?6%' \n"
        // + "and r.status like 'OPENING' \n"
        // + "order by (case \n"
        // + "when concat(r.jobLevel, ' ', p.name) like N'%?1%' then 1 \n"
        // + "when r.industry like N'%?2%' then 2 \n"
        // + "when r.jobLevel like N'%?3%' then 3 \n"
        // + "when r.typeOfWork like N'%?4%' then 4 \n"
        // + "when r.salary >= N'%?5%' then 5 \n"
        // + "when r.experience >= N'%?6%' then 6 \n"
        // + " end);", nativeQuery = true)
        @Modifying
        @Query(value = "SELECT r from RecruitmentRequest r join Position p on r.positionId = p.id \n"
                        + "where concat(r.job_level,' ', p.name) like N'%?1%' \n"
                        + "or r.industry N'%?2%' \n"
                        + "or r.jobLevel like N'%?3%' \n"
                        + "or r.typeOfWork like N'%?4%' \n"
                        + "or salary >= N'%?5%' \n"
                        + "or r.experience >= N'%?6%' \n"
                        + "and r.status like 'OPENING' \n"
                        + "order by (case \n"
                        + "when concat(r.jobLevel,' ', p.name) like N'%?1%' then 1 \n"
                        + "when r.industry like N'%?2%' then 2 \n"
                        + "when r.jobLevel like N'%?3%' then 3 \n"
                        + "when r.typeOfWork like N'%?4%' then 4 \n"
                        + "when  r.salary >= N'%?5%' then 5 \n"
                        + "when r.experience >= N'%?6%' then 6 \n"
                        + " end)", nativeQuery = true)
        List<RecruitmentRequest> filterRecruitmentRequest(String jobName, String industry, String jobLevel,
                        String typeOfWork, String salary, String experince);

}
