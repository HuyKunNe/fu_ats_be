package com.fu.fuatsbe.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class InterviewUpdateDTO {
    private String subject;
    private String purpose;
    private String date;
    private String time;
    private String address;
    private String room;
    private String linkMeeting;
    private String round;
    private String type;
    private String description;
    private int jobApplyId;
    private int candidateId;
    private List<Integer> employeeIds;
}
