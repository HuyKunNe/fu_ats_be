package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.*;
import com.fu.fuatsbe.response.LoginResponseDto;
import com.fu.fuatsbe.response.RegisterResponseDto;

import javax.management.relation.RoleNotFoundException;

public interface AuthService {
    public RegisterResponseDto registerByAdmin(RegisterDto employee) throws RoleNotFoundException;
    public RegisterResponseDto register(RegisterCandidateDto candidate) throws RoleNotFoundException;
    public LoginResponseDto login(LoginDto employee);
}
