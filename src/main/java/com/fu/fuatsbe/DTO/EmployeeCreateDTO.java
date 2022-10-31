package com.fu.fuatsbe.DTO;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fu.fuatsbe.constant.validation_message.ValidationMessage;
import com.fu.fuatsbe.constant.validation_size.ValidationSize;

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
public class EmployeeCreateDTO {

    @NotBlank(message = ValidationMessage.NAME_VALID_MESSAGE)
    private String name;
    private String image;
    @NotBlank(message = ValidationMessage.EMPLOYEE_CODE_VALID_MESSAGE)
    private String employeeCode;
    @NotBlank(message = ValidationMessage.GENDER_VALID_MESSAGE)
    private String gender;
    @NotBlank(message = ValidationMessage.DOB_VALID_MESSAGE)
    private Date dob;
    @NotBlank(message = ValidationMessage.PHONE_NOT_EMPTY_VALID_MESSAGE)
    @Size(min = ValidationSize.PHONE_MIN, max = ValidationSize.PHONE_MAX, message = ValidationMessage.PHONE_SIZE_VALID_MESSAGE)
    private String phone;
    @NotBlank(message = ValidationMessage.ADDRESS_VALID_MESSAGE)
    private String address;

}
