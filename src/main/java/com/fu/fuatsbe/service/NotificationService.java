package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.NotificationCreateDTO;
import com.fu.fuatsbe.DTO.SendNotificationDTO;

import javax.mail.MessagingException;

public interface NotificationService {

    public void sendNotificationForInterview(SendNotificationDTO sendNotificationDTO) throws MessagingException;
    void createNotification(NotificationCreateDTO notificationCreateDTO) throws MessagingException;

}
