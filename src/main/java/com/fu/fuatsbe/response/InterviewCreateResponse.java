package com.fu.fuatsbe.response;

import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.JobApply;
import com.fu.fuatsbe.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewCreateResponse {
    private String subject;
    private String purpose;
    private Date date;
    private String address;
    private String room;
    private String linkMeeting;
    private String round;
    private String description;
    private JobApply jobApply;
    private Candidate candidate;
    private List<Employee> employees;
    private List<Notification> notifications;
}
