package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.*;
import com.fu.fuatsbe.response.LoginResponseDto;
import com.fu.fuatsbe.response.RegisterResponseDto;

import javax.management.relation.RoleNotFoundException;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface AuthService {
    public RegisterResponseDto registerByAdmin(RegisterDto employee) throws RoleNotFoundException;

    public RegisterResponseDto register(RegisterCandidateDto candidate) throws RoleNotFoundException;

    public LoginResponseDto login(LoginDto employee);

    public void changePassword(ChangePasswordDTO changePasswordDTO);

    public LoginResponseDto loginGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken);

}
