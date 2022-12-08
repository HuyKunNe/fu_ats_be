package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.InviteReapplyDTO;

import javax.mail.MessagingException;

public interface EmailService {
    public void sendEmailInterview(String email, String title, String name, String content) throws MessagingException;
    public void sendEmailToGetBackPassword(String email) throws MessagingException;
    public void confirm(String email, String token);
    public void resetPassword(String email, String token, String password);

    void sendEmailToInviteReapply(InviteReapplyDTO inviteReapplyDTO)throws MessagingException;
}
