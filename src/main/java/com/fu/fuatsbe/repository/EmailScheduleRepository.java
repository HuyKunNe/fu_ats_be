package com.fu.fuatsbe.repository;

import com.fu.fuatsbe.entity.EmailSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface EmailScheduleRepository extends JpaRepository<EmailSchedule, Integer> {
    @Query(nativeQuery = true, value = "select * from email_schedule limit 1")
    EmailSchedule getFirstEmailSchedule();
}
