package com.fu.fuatsbe.DTO;

import com.fu.fuatsbe.constant.validation_message.ValidationMessage;
import com.fu.fuatsbe.constant.validation_size.ValidationSize;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class ChangePasswordDTO {
    @Email(message = ValidationMessage.EMAIL_VALID_MESSAGE)
    private String email;
    @NotBlank(message = ValidationMessage.PASSWORD_VALID_MESSAGE)
    private String oldPassword;
    @NotBlank(message = ValidationMessage.PASSWORD_VALID_MESSAGE)
    @Size(min = ValidationSize.PASSWORD_MIN, max = ValidationSize.PASSWORD_MAX, message = ValidationMessage.PASSWORD_SIZE_VALID_MESSAGE)
    private String newPassword;
}
