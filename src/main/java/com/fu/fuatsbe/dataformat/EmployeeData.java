package com.fu.fuatsbe.dataformat;

import com.fu.fuatsbe.response.EmployeeResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeData {

    private String message;
    private EmployeeResponse data;
    private String status;
}
