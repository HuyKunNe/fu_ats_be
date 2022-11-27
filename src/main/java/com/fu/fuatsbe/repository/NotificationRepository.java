package com.fu.fuatsbe.repository;

import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.Notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    public Notification findById(int id);

    public Page<Notification> findNotificationByEmployees(Employee employee, Pageable pageable);

    public Page<Notification> findNotificationByCandidates(Candidate candidate, Pageable pageable);

}
