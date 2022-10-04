package com.fu.fuatsbe.controller;

import javax.annotation.security.PermitAll;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fu.fuatsbe.DTO.RoleCreateDTO;
import com.fu.fuatsbe.DTO.RoleUpdateDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.dataformat.ListRoleData;
import com.fu.fuatsbe.dataformat.RoleData;
import com.fu.fuatsbe.entity.Role;
import com.fu.fuatsbe.repository.RoleRepository;
import com.fu.fuatsbe.response.RoleResponse;
import com.fu.fuatsbe.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("")
@RequiredArgsConstructor
public class RoleController {

    private RoleService roleService;
    private RoleRepository roleRepository;

    @GetMapping("/role/getAll")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public ListRoleData getAllRoles() {
        ListRoleData result = new ListRoleData();
        try {
            result.setData(roleService.getAllRoles());
            if (result.getData().isEmpty()) {
                result.setMessage("list is empty");
                result.setStatus("SUCCESS");
            } else {
                result.setMessage("get all roles is successfully");
                result.setStatus("SUCCESS");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus("ERROR");
        }
        return result;
    }

    @GetMapping("/role/getById/{id}")
    public RoleData getRoleById(@PathVariable int id) {
        RoleData result = new RoleData();
        try {
            result.setData(roleService.getRoleById(id));
            if (result.getData() != null) {
                result.setMessage("find role by id successfully");
                result.setStatus("SUCCESS");
            } else {
                result.setMessage("role id is not exist");
                result.setStatus("SUCCESS");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus("ERROR");
        }
        return result;
    }

    @PostMapping("/role/create")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public RoleData createRole(@RequestBody RoleCreateDTO createDTO) {
        RoleData result = new RoleData();
        try {
            Role role = roleService.createRole(createDTO);
            RoleResponse roleResponse = new RoleResponse();
            if (role != null) {
                roleResponse.setId(role.getId());
                roleResponse.setName(role.getName());

                result.setMessage("create role successfully");
                result.setData(roleResponse);
                result.setStatus("SUCCESS");
            } else {
                result.setMessage("create role failure");
                result.setStatus("FAILURE");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus("ERROR");
        }
        return result;
    }

    @PutMapping("/role/edit/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public RoleData updateRole(@PathVariable int id, @RequestBody RoleUpdateDTO updateDTO) {
        RoleData result = new RoleData();
        try {
            if (roleRepository.existsById(id)) {
                RoleResponse roleResponse = roleService.updateRole(id, updateDTO);
                if (roleResponse != null) {
                    result.setData(roleResponse);
                    result.setStatus("SUCCESS");
                    result.setMessage("role updated successfully");
                }
            } else {
                result.setMessage("Role is not exist");
                result.setStatus("FAILURE");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus("ERROR");
        }
        return result;
    }
}
