package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.DTO.*;
import com.fu.fuatsbe.constant.employee.EmployeeSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.LoginResponseDto;
import com.fu.fuatsbe.response.RegisterResponseDto;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.service.AuthService;
import com.fu.fuatsbe.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.mail.MessagingException;
import javax.management.relation.RoleNotFoundException;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Validated @RequestBody LoginDto employee) {
        ResponseDTO<LoginResponseDto> responseDTO = new ResponseDTO();
        LoginResponseDto loginResponseDTO = authService.login(employee);
        responseDTO.setData(loginResponseDTO);
        responseDTO.setMessage("Login success");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Validated @RequestBody RegisterCandidateDto candidate)
            throws RoleNotFoundException {
        ResponseDTO<RegisterResponseDto> responseDTO = new ResponseDTO();
        RegisterResponseDto registerResponseDto = authService.register(candidate);
        responseDTO.setData(registerResponseDto);
        responseDTO.setMessage("Register success");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/registerByAdmin")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public ResponseEntity<ResponseDTO> registerByAdmin(@Validated @RequestBody RegisterDto user)
            throws RoleNotFoundException {
        ResponseDTO<RegisterResponseDto> responseDTO = new ResponseDTO();
        RegisterResponseDto registerResponseDto = authService.registerByAdmin(user);
        responseDTO.setData(registerResponseDto);
        responseDTO.setMessage("Register success");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
    @PermitAll
    @GetMapping("forgot-password")
    public ResponseEntity<ResponseDTO> sendEmailToGetPassword(@RequestParam String email) throws MessagingException {
        ResponseDTO<Void> responseDTO = new ResponseDTO();
        emailService.sendEmailToGetBackPassword(email);
        responseDTO.setMessage(EmployeeSuccessMessage.SEND_LINK_VERIFY_TO_GET_BACK_PASSWORD_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
    @PermitAll
    @PatchMapping("/reset-password")
    public ResponseEntity<ResponseDTO> resetPassword(@Validated @RequestBody ResetPasswordDto resetPasswordDto){
        ResponseDTO<Void> responseDTO = new ResponseDTO();
        emailService.resetPassword(resetPasswordDto.getEmail(), resetPasswordDto.getToken(), resetPasswordDto.getNewPassword());
        responseDTO.setMessage(EmployeeSuccessMessage.RESET_PASSWORD_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
