package com.fu.fuatsbe.dataformat;

import java.util.List;

import com.fu.fuatsbe.response.EmployeeResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListEmployeeData {

    private String message;
    private List<EmployeeResponse> data;
    private String status;
}
