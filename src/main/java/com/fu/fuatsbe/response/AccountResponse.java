package com.fu.fuatsbe.response;

import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private int id;
    private String email;
    private String status;
    private Role role;
    private Employee employee;
    private Candidate candidate;
}
