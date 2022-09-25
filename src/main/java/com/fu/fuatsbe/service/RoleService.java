package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.RoleCreateDTO;
import com.fu.fuatsbe.DTO.RoleUpdateDTO;
import com.fu.fuatsbe.entity.Role;
import com.fu.fuatsbe.response.RoleResponse;

public interface RoleService {
    
    List<RoleResponse> getAllRoles();

    RoleResponse getRoleById(int id);

    Role createRole(RoleCreateDTO createDTO);

    RoleResponse updateRole(int id, RoleUpdateDTO updateDTO);

    boolean deleteRoleById(int id);
}
