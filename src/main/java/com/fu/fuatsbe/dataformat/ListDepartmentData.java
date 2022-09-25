package com.fu.fuatsbe.dataformat;

import java.util.List;

import com.fu.fuatsbe.response.DepartmentResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListDepartmentData {

    private String message;
    private List<DepartmentResponse> data;
    private String status;
}
