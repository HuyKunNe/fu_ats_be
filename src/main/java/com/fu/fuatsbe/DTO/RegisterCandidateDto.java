package com.fu.fuatsbe.DTO;

import com.fu.fuatsbe.constant.validation_message.ValidationMessage;
import com.fu.fuatsbe.constant.validation_size.ValidationSize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterCandidateDto {
    @NotBlank(message = ValidationMessage.PASSWORD_VALID_MESSAGE)
    @Size(min = ValidationSize.PASSWORD_MIN, max = ValidationSize.PASSWORD_MAX, message = ValidationMessage.PASSWORD_SIZE_VALID_MESSAGE)
    private String password;
    @Email(message = ValidationMessage.EMAIL_VALID_MESSAGE)
    private String email;

    @NotBlank(message = ValidationMessage.NAME_VALID_MESSAGE)
    private String name;

    private String image;
    private String gender;
    private Date dob;

    @NotBlank(message = ValidationMessage.PHONE_NOT_EMPTY_VALID_MESSAGE)
    @Size(min = ValidationSize.PHONE_MIN, max = ValidationSize.PHONE_MAX, message = ValidationMessage.PHONE_SIZE_VALID_MESSAGE)
    private String phone;
    @NotBlank(message = ValidationMessage.ADDRESS_VALID_MESSAGE)
    private String address;

}
