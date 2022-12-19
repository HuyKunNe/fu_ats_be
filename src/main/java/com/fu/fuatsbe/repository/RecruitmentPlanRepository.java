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
import com.fu.fuatsbe.entity.RecruitmentPlan;

@Repository
@Transactional
public interface RecruitmentPlanRepository extends JpaRepository<RecruitmentPlan, Integer> {

        Optional<RecruitmentPlan> findById(int id);

        Page<RecruitmentPlan> findByStatus(String status, Pageable pageable);

        Page<RecruitmentPlan> findByApprover(Employee employee, Pageable pageable);

        Page<RecruitmentPlan> findByCreator(Employee employee, Pageable pageable);

        @Query(nativeQuery = true, value = "select * from recruitment_plan where creator_id in" +
                        "(select id from employee where department_id = ?1)")
        Page<RecruitmentPlan> findByDepartmentId(int departmentId, Pageable pageable);

        @Query(nativeQuery = true, value = "select * from recruitment_plan r where r.creator_id " +
                        "in(select id from employee where department_id = ?1) " +
                        "and status like 'APPROVED' and CURDATE() <= r.period_to ")
        List<RecruitmentPlan> findApprovedByDepartment(int id);

        @Query(nativeQuery = true, value = "(select  coalesce(status, 'PENDING') as status,count(status) as total from recruitment_plan where status like 'PENDING') "
                        +
                        "union (select  coalesce(status, 'APPROVED') ,count(status)  from recruitment_plan where status like 'APPROVED') "
                        +
                        "union(select  coalesce(status, 'REJECTED') ,count(status) from recruitment_plan where status like'REJECTED')")
        List<Tuple> getTotalStatus();

        @Query(nativeQuery = true, value = "select COALESCE(sum(cast(replace(p.salary, ',', '') as unsigned) * p.amount),0) \n"
                        + " from plan_detail p join recruitment_plan r on p.recruitment_plan_id = r.id \n"
                        + " where p.status like 'APPROVED' and r.id = ?1")
        int totalSalaryFund(int recruitment_plan_id);

        @Query(nativeQuery = true, value = "SELECT distinct(year(rp.period_from)) FROM fu_ats.recruitment_plan rp;")
        List<String> getYearFromPlan();

}
