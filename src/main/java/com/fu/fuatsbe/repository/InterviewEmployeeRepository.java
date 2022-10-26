package com.fu.fuatsbe.repository;

import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.InterviewEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewEmployeeRepository extends JpaRepository<InterviewEmployee, String> {



}
