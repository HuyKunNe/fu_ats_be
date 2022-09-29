package com.fu.fuatsbe.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentResponse {
    private int id;
    private String room;
    private String name;
    private String phone;
    private String status;
}
