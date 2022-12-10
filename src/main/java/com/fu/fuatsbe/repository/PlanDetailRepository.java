package com.fu.fuatsbe.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.PlanDetail;
import com.fu.fuatsbe.entity.RecruitmentPlan;

@Transactional
@Repository
public interface PlanDetailRepository extends JpaRepository<PlanDetail, Integer> {
        Optional<PlanDetail> findById(int id);

        Page<PlanDetail> findByStatus(String status, Pageable pageable);

        Page<PlanDetail> findByRecruitmentPlan(RecruitmentPlan recruitmentPlan, Pageable pageable);

        Page<PlanDetail> findByApprover(Employee employee, Pageable pageable);

        @Query(value = "select coalesce(sum(p.amount),0) as totalAmount from plan_detail p \n"
                        + " join recruitment_plan r on p.recruitment_plan_id = r.id \n"
                        + " where p.status like '%APPROVED%' and r.id = ?1", nativeQuery = true)
        Integer totalAmount(int id);

        @Query(nativeQuery = true, value = "select * from plan_detail where creator_id " +
                        "in(select id from employee where department_id = ?1) and status like 'APPROVED';")
        List<PlanDetail> findApprovedByDepartment(int departmentId);

        @Query(nativeQuery = true, value = "(select coalesce(status, 'PENDING')  as status, count(status) as total from plan_detail where status like 'PENDING') "
                        +
                        "union (select  coalesce(status, 'APPROVED') , count(status)  from plan_detail where status like 'APPROVED') "
                        +
                        "union( select  coalesce(status, 'REJECTED') ,count(status) from plan_detail where status like'REJECTED')")
        List<Tuple> getTotalStatusDetail();

        @Query(nativeQuery = true, value = "select * from plan_detail where creator_id in(select id from employee where department_id = ?1) order by date desc")
        Page<PlanDetail> getPlanDetailByDepartment(int departmentId, Pageable pageable);
}
