package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.RoleCreateDTO;
import com.fu.fuatsbe.DTO.RoleUpdateDTO;
import com.fu.fuatsbe.entity.Role;
import com.fu.fuatsbe.repository.RoleRepository;
import com.fu.fuatsbe.response.RoleResponse;
import com.fu.fuatsbe.service.RoleService;

@Service
public class RoleServiceImp implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<Role> list = roleRepository.findAll();
        List<RoleResponse> result = new ArrayList<RoleResponse>();
        if (list.size() > 0)
            for (Role role : list) {
                RoleResponse roleResponse = new RoleResponse();
                roleResponse.setId(role.getId());
                roleResponse.setName(role.getName());
                result.add(roleResponse);
            }
        return result;
    }

    @Override
    public RoleResponse getRoleById(int id) {
        RoleResponse result = new RoleResponse();
        if (roleRepository.existsById(id)) {
            Role role = roleRepository.findById(id).get();
            result.setId(id);
            result.setName(role.getName());
        } else
            result = null;
        return result;
    }

    @Override
    public Role createRole(RoleCreateDTO createDTO) {
        Role role = new Role();
        role.setName(createDTO.getName());
        return roleRepository.save(role);
    }

    @Override
    public RoleResponse updateRole(int id, RoleUpdateDTO updateDTO) {
        RoleResponse result = new RoleResponse();
        Role role = roleRepository.findById(id).get();
        role.setName(updateDTO.getName());
        roleRepository.save(role);
        result.setId(id);
        result.setName(role.getName());
        return result;
    }

}
