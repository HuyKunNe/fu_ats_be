package com.fu.fuatsbe.service;

import java.util.List;

import javax.mail.MessagingException;

import com.fu.fuatsbe.DTO.NotificationCreateDTO;
import com.fu.fuatsbe.DTO.SendNotificationDTO;
import com.fu.fuatsbe.entity.Notification;

public interface NotificationService {

    public void sendNotificationForInterview(SendNotificationDTO sendNotificationDTO) throws MessagingException;

    void createNotification(NotificationCreateDTO notificationCreateDTO) throws MessagingException;

    public List<Notification> getAllByCandidate(int candidateId);

    public List<Notification> getAllByEmployee(int employeeId);

}
