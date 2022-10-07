package com.fu.fuatsbe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fu.fuatsbe.DTO.RoleCreateDTO;
import com.fu.fuatsbe.DTO.RoleUpdateDTO;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.constant.role.RoleSuccessMessage;
import com.fu.fuatsbe.response.ListResponseDTO;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.response.RoleResponse;
import com.fu.fuatsbe.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("")
@RequiredArgsConstructor
public class RoleController {

    private RoleService roleService;

    @GetMapping("/role/getAll")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public ResponseEntity<ListResponseDTO> getAllRoles() {
        ListResponseDTO<RoleResponse> response = new ListResponseDTO();
        List<RoleResponse> list = roleService.getAllRoles();
        response.setData(list);
        response.setMessage(RoleSuccessMessage.GET_ALL_ROLE);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/role/getById")
    public ResponseEntity<ResponseDTO> getRoleById(@RequestParam("id") int id) {
        ResponseDTO<RoleResponse> responseDTO = new ResponseDTO();
        RoleResponse role = roleService.getRoleById(id);
        responseDTO.setData(role);
        responseDTO.setMessage(RoleSuccessMessage.GET_ROLE_BY_ID);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/role/create")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public ResponseEntity<ResponseDTO> createRole(@RequestBody RoleCreateDTO createDTO) {
        ResponseDTO<RoleResponse> responseDTO = new ResponseDTO();
        RoleResponse role = roleService.createRole(createDTO);
        responseDTO.setData(role);
        responseDTO.setMessage("create role success");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/role/edit")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public ResponseEntity<ResponseDTO> updateRole(@RequestParam("id") int id, @RequestBody RoleUpdateDTO updateDTO) {
        ResponseDTO<RoleResponse> responseDTO = new ResponseDTO();
        RoleResponse role = roleService.updateRole(id, updateDTO);
        responseDTO.setData(role);
        responseDTO.setMessage("update role success");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
