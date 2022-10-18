package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.RoleCreateDTO;
import com.fu.fuatsbe.DTO.RoleUpdateDTO;
import com.fu.fuatsbe.response.RoleResponse;

public interface RoleService {

    public List<RoleResponse> getAllRoles();

    public RoleResponse getRoleById(int id);

    public RoleResponse createRole(RoleCreateDTO createDTO);

    public RoleResponse updateRole(int id, RoleUpdateDTO updateDTO);

}
