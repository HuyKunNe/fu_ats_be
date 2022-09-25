package com.fu.fuatsbe.dataformat;

import com.fu.fuatsbe.response.RoleResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleData {
    
    private String message;
    private RoleResponse data;
    private String status;
}
