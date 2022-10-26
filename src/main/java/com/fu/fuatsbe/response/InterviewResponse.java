package com.fu.fuatsbe.response;

import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.JobApply;
import com.fu.fuatsbe.entity.Notification;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewResponse {
    private int id;
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
}