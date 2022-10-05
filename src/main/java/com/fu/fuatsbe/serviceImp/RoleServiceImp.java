package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.RoleCreateDTO;
import com.fu.fuatsbe.DTO.RoleUpdateDTO;
import com.fu.fuatsbe.constant.role.RoleErrorMessage;
import com.fu.fuatsbe.entity.Role;
import com.fu.fuatsbe.repository.RoleRepository;
import com.fu.fuatsbe.response.RoleResponse;
import com.fu.fuatsbe.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {

    private RoleRepository roleRepository;
    private ModelMapper modelMapper;

    @Override
    public List<RoleResponse> getAllRoles() {
        List<Role> list = roleRepository.findAll();
        List<RoleResponse> result = new ArrayList<RoleResponse>();
        if (list.size() > 0) {
            for (Role role : list) {
                RoleResponse response = modelMapper.map(role, RoleResponse.class);
                result.add(response);
            }
        } else
            throw new IllegalStateException(RoleErrorMessage.LIST_ROLE_EMPTY);
        return result;
    }

    @Override
    public RoleResponse getRoleById(int id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        RoleErrorMessage.ROLE_NOT_EXIST));
        RoleResponse response = modelMapper.map(role, RoleResponse.class);
        return response;
    }

    @Override
    public RoleResponse createRole(RoleCreateDTO createDTO) {
        Optional<Role> optionalRole = roleRepository.findByName(createDTO.getName());
        if (optionalRole.isPresent())
            throw new IllegalStateException(RoleErrorMessage.ROLE_EXIST_EXCEPTION);
        else {
            Role role = Role.builder().name(createDTO.getName()).build();
            roleRepository.save(role);
            RoleResponse response = modelMapper.map(role, RoleResponse.class);
            return response;
        }
    }

    @Override
    public RoleResponse updateRole(int id, RoleUpdateDTO updateDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        RoleErrorMessage.ROLE_NOT_EXIST));
        role.setName(updateDTO.getName());
        roleRepository.save(role);
        RoleResponse response = modelMapper.map(role, RoleResponse.class);
        return response;
    }

}
