package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.DTO.*;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.LoginResponseDto;
import com.fu.fuatsbe.response.RegisterResponseDto;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Validated @RequestBody LoginDto employee) {
        ResponseDTO<LoginResponseDto> responseDTO = new ResponseDTO();
        LoginResponseDto loginResponseDTO = authService.login(employee);
        responseDTO.setData(loginResponseDTO);
        responseDTO.setSuccessMessage("Login success");
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Validated @RequestBody RegisterCandidateDto candidate)
            throws RoleNotFoundException {
        ResponseDTO<RegisterResponseDto> responseDTO = new ResponseDTO();
        RegisterResponseDto registerResponseDto = authService.register(candidate);
        responseDTO.setData(registerResponseDto);
        responseDTO.setSuccessMessage("Register success");
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/registerByAdmin")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public ResponseEntity<ResponseDTO> registerByAdmin(@Validated @RequestBody RegisterDto user)
            throws RoleNotFoundException {
        ResponseDTO<RegisterResponseDto> responseDTO = new ResponseDTO();
        RegisterResponseDto registerResponseDto = authService.registerByAdmin(user);
        responseDTO.setData(registerResponseDto);
        responseDTO.setSuccessMessage("Register success");
        return ResponseEntity.ok().body(responseDTO);
    }
}
