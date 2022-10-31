package com.fu.fuatsbe.DTO;

import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Interview;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Builder
public class SendNotificationDTO {
    private String link;
    private String room;
    private String address;
    private Date date;
    private Candidate candidate;
    private List<Integer> IntervieweeID;
    private Interview interview;
}
