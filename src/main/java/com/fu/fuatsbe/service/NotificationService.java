package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.SendNotificationDTO;
import com.fu.fuatsbe.entity.Notification;
import com.fu.fuatsbe.response.ResponseDTO;

import javax.mail.MessagingException;

public interface NotificationService {

    public void sendNotificationForInterview(SendNotificationDTO sendNotificationDTO) throws MessagingException;
}
