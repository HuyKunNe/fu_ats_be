package com.fu.fuatsbe.DTO;

import com.fu.fuatsbe.constant.validation_message.ValidationMessage;

import javax.validation.constraints.NotBlank;

import java.util.List;

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
public class InterviewCreateDTO {

    @NotBlank(message = ValidationMessage.SUBJECT_VALID_MESSAGE)
    private String subject;
    @NotBlank(message = ValidationMessage.PURPOSE_VALID_MESSAGE)
    private String purpose;
    @NotBlank(message = ValidationMessage.DATE_VALID_MESSAGE)
    private String date;

    private String time;

    private String address;

    private String room;

    private String linkMeeting;

    private String round;
    @NotBlank(message = ValidationMessage.INTERVIEW_TYPE_VALID_MESSAGE)
    private String type;
    @NotBlank(message = ValidationMessage.DESCRIPTION_VALID_MESSAGE)
    private String description;
    @NotBlank(message = ValidationMessage.RECRUITMENT_REQUEST_VALID_MESSAGE)
    private int recruitmentRequestId;
    @NotBlank(message = ValidationMessage.CANDIDATE_ID_VALID_MESSAGE)
    private List<Integer> candidateId;
    @NotBlank(message = ValidationMessage.INTERVIEWEE_ID_VALID_MESSAGE)
    private List<Integer> employeeId;
}
