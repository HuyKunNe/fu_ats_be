package com.fu.fuatsbe.repository;

import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.Notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    public Notification findById(int id);

    public Page<Notification> findNotificationByEmployees(Employee employee, Pageable pageable);

    public Page<Notification> findNotificationByCandidates(Candidate candidate, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from notification where is_mail_send_candidate = false and " +
            "type like 'INTERVIEW' and id in(select notifice_id from notified_candidate where candidate_id = ?1)")
    List<Notification> findNotiNotSendByCandidate(int candidateId);
    @Query(nativeQuery = true, value = "select content, subject from notification where interview_id = ?1")
    Tuple getNotificationByInterview(int interviewId);

}
