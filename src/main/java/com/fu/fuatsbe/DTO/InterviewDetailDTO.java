package com.fu.fuatsbe.DTO;

import java.sql.Date;

import javax.validation.constraints.NotBlank;

import com.fu.fuatsbe.constant.validation_message.ValidationMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InterviewDetailDTO {

    @NotBlank(message = ValidationMessage.END_DATE_VALID_MESSAGE)
    private Date end;
    @NotBlank(message = ValidationMessage.RESULT_VALID_MESSAGE)
    private String result;
    @NotBlank(message = ValidationMessage.DESCRIPTION_VALID_MESSAGE)
    private String description;
    private String recommendPositions;
    private String note;
    private String recordMeeting;
    @NotBlank(message = ValidationMessage.INTERVIEW_ID_VALID_MESSAGE)
    private int interviewID;
}
