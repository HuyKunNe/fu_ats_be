package com.fu.fuatsbe.DTO;

import com.fu.fuatsbe.constant.validation_message.ValidationMessage;
import com.fu.fuatsbe.entity.JobApply;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.List;

@Data
@Builder
public class InterviewCreateDTO {

    @NotBlank(message = ValidationMessage.SUBJECT_VALID_MESSAGE)
    private String subject;
    @NotBlank(message = ValidationMessage.PURPOSE_VALID_MESSAGE)
    private String purpose;
    @NotBlank(message = ValidationMessage.DATE_VALID_MESSAGE)
    private Date date;

    private String address;

    private String room;

    private String linkMeeting;

    private String round;
    @NotBlank(message = ValidationMessage.DESCRIPTION_VALID_MESSAGE)
    private String description;
    @NotBlank(message = ValidationMessage.JOB_APPLY_VALID_MESSAGE)
    private int JobApplyId;
    @NotBlank(message = ValidationMessage.CANDIDATE_ID_VALID_MESSAGE)
    private int candidateId;
    @NotBlank(message = ValidationMessage.INTERVIEWEE_ID_VALID_MESSAGE)
    private List<Integer> employeeId;
}
