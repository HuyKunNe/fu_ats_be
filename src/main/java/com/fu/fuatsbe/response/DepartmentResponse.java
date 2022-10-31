package com.fu.fuatsbe.response;

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
public class DepartmentResponse {
    private int id;
    private String room;
    private String name;
    private String phone;
    private String status;
}
