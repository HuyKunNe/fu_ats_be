package com.fu.fuatsbe.repository;

import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.JobApply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobApplyRepository extends JpaRepository<JobApply, Integer> {
    public Optional<JobApply> findById(int id);
}
