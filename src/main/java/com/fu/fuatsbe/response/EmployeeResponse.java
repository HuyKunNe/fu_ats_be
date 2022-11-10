package com.fu.fuatsbe.response;

import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.entity.Position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

    private int id;
    private String name;
    private String employeeCode;
    private String image;
    private String phone;
    private String address;
    private String status;
    private Department department;
    private Position position;
}
