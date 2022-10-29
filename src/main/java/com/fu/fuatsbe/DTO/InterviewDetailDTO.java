package com.fu.fuatsbe.DTO;

import com.fu.fuatsbe.constant.validation_message.ValidationMessage;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

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
    private String recordMeeting;
    @NotBlank(message = ValidationMessage.INTERVIEW_ID_VALID_MESSAGE)
    private int interviewID;
}
