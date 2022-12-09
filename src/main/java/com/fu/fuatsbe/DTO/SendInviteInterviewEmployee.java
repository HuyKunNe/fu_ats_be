package com.fu.fuatsbe.DTO;

import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.Interview;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendInviteInterviewEmployee {
    private String link;
    private String room;
    private String address;
    private String jobName;
    private String date;
    private String time;
    private Employee employee;
}
