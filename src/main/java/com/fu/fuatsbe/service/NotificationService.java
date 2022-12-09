package com.fu.fuatsbe.service;

import javax.mail.MessagingException;

import com.fu.fuatsbe.DTO.NotificationCreateDTO;
import com.fu.fuatsbe.DTO.SendInviteInterviewEmployee;
import com.fu.fuatsbe.DTO.SendNotificationDTO;
import com.fu.fuatsbe.entity.Notification;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

public interface NotificationService {

    public void sendNotificationForInterview(SendNotificationDTO sendNotificationDTO) throws MessagingException;
    public void sendInterviewNotiForEmployee(SendInviteInterviewEmployee sendInviteInterviewEmployee);

    void createNotification(NotificationCreateDTO notificationCreateDTO) throws MessagingException;

    public ResponseWithTotalPage<Notification> getAllByCandidate(int candidateId, int pageNo, int pageSize);

    public ResponseWithTotalPage<Notification> getAllByEmployee(int employeeId, int pageNo, int pageSize);

}
