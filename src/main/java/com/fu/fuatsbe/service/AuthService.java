package com.fu.fuatsbe.service;

import javax.management.relation.RoleNotFoundException;

import org.springframework.boot.configurationprocessor.json.JSONException;

import com.fu.fuatsbe.DTO.ChangePasswordDTO;
import com.fu.fuatsbe.DTO.LoginDto;
import com.fu.fuatsbe.DTO.RegisterCandidateDto;
import com.fu.fuatsbe.DTO.RegisterDto;
import com.fu.fuatsbe.entity.Account;
import com.fu.fuatsbe.response.LoginResponseDto;
import com.fu.fuatsbe.response.RegisterResponseDto;

public interface AuthService {
    public RegisterResponseDto registerByAdmin(RegisterDto employee) throws RoleNotFoundException;

    public RegisterResponseDto register(RegisterCandidateDto candidate) throws RoleNotFoundException;

    public LoginResponseDto login(LoginDto employee);

    public void changePassword(ChangePasswordDTO changePasswordDTO);

    public LoginResponseDto loginWithGoogle(String token) throws JSONException;

    public Account registerAccountForGoogleLogin(String email, String name, String image);
}
