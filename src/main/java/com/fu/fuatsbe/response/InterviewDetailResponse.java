package com.fu.fuatsbe.response;

import com.fu.fuatsbe.entity.Interview;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewDetailResponse {
    private int id;
    private Date start;
    private Date end;
    private String result;
    private String recordMeeting;
    private String description;
    private Interview interview;
}
