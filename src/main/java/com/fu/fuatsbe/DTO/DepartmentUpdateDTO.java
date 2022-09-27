package com.fu.fuatsbe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentUpdateDTO {
    private String room;
    private String name;
    private String phone;
    private int status;
}
