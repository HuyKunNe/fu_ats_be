package com.fu.fuatsbe.serviceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.RoleCreateDTO;
import com.fu.fuatsbe.DTO.RoleUpdateDTO;
import com.fu.fuatsbe.entity.Role;
import com.fu.fuatsbe.response.RoleResponse;
import com.fu.fuatsbe.service.RoleService;

@Service
public class RoleServiceImp implements RoleService {

    @Override
    public List<RoleResponse> getAllRoles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RoleResponse getRoleById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Role createRole(RoleCreateDTO createDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RoleResponse updateRole(int id, RoleUpdateDTO updateDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteRoleById(int id) {
        // TODO Auto-generated method stub
        return false;
    }

}
