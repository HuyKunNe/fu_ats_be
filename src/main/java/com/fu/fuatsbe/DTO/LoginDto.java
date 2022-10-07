package com.fu.fuatsbe.DTO;

import com.fu.fuatsbe.constant.validation_message.ValidationMessage;
import com.fu.fuatsbe.constant.validation_size.ValidationSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginDto {

    @Email(message = ValidationMessage.EMAIL_VALID_MESSAGE_WHEN_LOGIN, regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$/")
    private String email;
    @Size(min = ValidationSize.PASSWORD_MIN, max = ValidationSize.PASSWORD_MAX, message = ValidationMessage.PASSWORD_VALID_MESSAGE)
    private String password;
}
