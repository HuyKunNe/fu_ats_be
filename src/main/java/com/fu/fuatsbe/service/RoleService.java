package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.RoleCreateDTO;
import com.fu.fuatsbe.DTO.RoleUpdateDTO;
import com.fu.fuatsbe.response.RoleResponse;

public interface RoleService {

    List<RoleResponse> getAllRoles();

    RoleResponse getRoleById(int id);

    RoleResponse createRole(RoleCreateDTO createDTO);

    RoleResponse updateRole(int id, RoleUpdateDTO updateDTO);

}
