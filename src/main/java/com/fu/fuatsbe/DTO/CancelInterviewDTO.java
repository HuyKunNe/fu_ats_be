package com.fu.fuatsbe.DTO;

import com.fu.fuatsbe.constant.validation_message.ValidationMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CancelInterviewDTO {
    @NotBlank(message = ValidationMessage.INTERVIEW_ID_VALID_MESSAGE)
    private int interviewId;
    @NotBlank(message = ValidationMessage.EMPLOYEE_ID_VALID_MESSAGE)
    private int employeeId;
    @NotBlank(message = ValidationMessage.REASON_CANCEL_INTERVIEW_VALID)
    private String reason;
}
