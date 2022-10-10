package com.fu.fuatsbe.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fu.fuatsbe.constant.validation_message.ValidationMessage;
import com.fu.fuatsbe.constant.validation_size.ValidationSize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCreateDTO {

    @NotBlank(message = ValidationMessage.NAME_VALID_MESSAGE)
    private String name;
    private String image;
    private String employeeCode;
    @NotBlank(message = ValidationMessage.PHONE_NOT_EMPTY_VALID_MESSAGE)
    @Size(min = ValidationSize.PHONE_MIN, max = ValidationSize.PHONE_MAX, message = ValidationMessage.PHONE_SIZE_VALID_MESSAGE)
    private String phone;
    @NotBlank(message = ValidationMessage.ADDRESS_VALID_MESSAGE)
    private String address;

}
