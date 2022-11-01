package com.fu.fuatsbe.response;

import com.fu.fuatsbe.entity.JobApply;
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
    private String status;
    private String type;
    private JobApply jobApply;
    private String candidateName;
    private List<String> employeeNames;
}
