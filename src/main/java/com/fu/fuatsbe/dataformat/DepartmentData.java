package com.fu.fuatsbe.dataformat;

import com.fu.fuatsbe.response.DepartmentResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentData {

    private String message;
    private DepartmentResponse data;
    private String status;
}
