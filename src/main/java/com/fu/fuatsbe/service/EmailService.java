package com.fu.fuatsbe.service;

import javax.mail.MessagingException;

public interface EmailService {
    public void sendEmail();
    public void sendEmailToGetBackPassword(String email) throws MessagingException;
    public void confirm(String email, String token);
    public void resetPassword(String email, String token, String password);

    void sendEmailToInviteReapply(String email, String candidateName, String JobName)throws MessagingException;
}
