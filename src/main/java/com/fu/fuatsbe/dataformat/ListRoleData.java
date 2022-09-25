package com.fu.fuatsbe.dataformat;

import java.util.List;

import com.fu.fuatsbe.response.RoleResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListRoleData {

    private String message;
    private List<RoleResponse> data;
    private String status;
}
