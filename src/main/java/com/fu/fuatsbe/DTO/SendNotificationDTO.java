package com.fu.fuatsbe.DTO;

import java.sql.Timestamp;
import java.util.List;

import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Interview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendNotificationDTO {
    private String link;
    private String room;
    private String address;
    private Timestamp date;
    private Candidate candidate;
    private List<Integer> IntervieweeID;
    private Interview interview;
}
