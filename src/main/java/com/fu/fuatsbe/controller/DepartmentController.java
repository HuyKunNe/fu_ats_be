package com.fu.fuatsbe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fu.fuatsbe.DTO.DepartmentCreateDTO;
import com.fu.fuatsbe.DTO.DepartmentUpdateDTO;
import com.fu.fuatsbe.constant.department.DepartmentSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.response.DepartmentResponse;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/department")
@CrossOrigin("*")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("getAll")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllDepartments(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "") String name) {
        ResponseDTO<ResponseWithTotalPage> response = new ResponseDTO();
        ResponseWithTotalPage<DepartmentResponse> list = departmentService.getAllDepartments(pageNo, pageSize, name);
        response.setData(list);
        response.setMessage(DepartmentSuccessMessage.GET_ALL_DEPARTMENT);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("getById/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getDepartmentById(@RequestParam("id") int id) {
        ResponseDTO<DepartmentResponse> responseDTO = new ResponseDTO();
        DepartmentResponse departmentResponse = departmentService.getDepartmentById(id);
        responseDTO.setData(departmentResponse);
        responseDTO.setMessage(DepartmentSuccessMessage.GET_DEPARTMENT_BY_ID);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("getByName")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getDepartmentByName(
            @RequestParam("name") String name,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> response = new ResponseDTO();
        ResponseWithTotalPage<DepartmentResponse> list = departmentService.getDepartmentByName(name, pageNo, pageSize);
        response.setData(list);
        response.setMessage(DepartmentSuccessMessage.GET_DEPARTMENT_BY_NAME);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/getIdName")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getDepartmentIdAndName() {
        ResponseDTO response = new ResponseDTO();
        response.setData(departmentService.getDepartmentName());
        response.setMessage(DepartmentSuccessMessage.GET_DEPARTMENT_ID_NAME);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("edit/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> updateDepartment(@RequestParam("id") int id,
            @RequestBody DepartmentUpdateDTO updateDTO) {
        ResponseDTO<DepartmentResponse> responseDTO = new ResponseDTO();
        DepartmentResponse department = departmentService.updateDepartment(id, updateDTO);
        responseDTO.setData(department);
        responseDTO.setMessage(DepartmentSuccessMessage.UPDATE_DEPARTMENT_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("create")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public ResponseEntity<ResponseDTO> createDepartment(
            @RequestBody DepartmentCreateDTO createDTO) {
        ResponseDTO<Department> responseDTO = new ResponseDTO();
        Department department = departmentService.createDepartment(createDTO);
        responseDTO.setData(department);
        responseDTO.setMessage(DepartmentSuccessMessage.CREATE_DEPARTMENT_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public ResponseEntity deleteDepartmnetById(@RequestParam("id") int id) {
        ResponseDTO<Department> responseDTO = new ResponseDTO();
        departmentService.deleteDepartmentById(id);
        responseDTO.setMessage(DepartmentSuccessMessage.DELETE_DEPARTMENT_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
    @PatchMapping("/active/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public ResponseEntity activeDepartmnetById(@RequestParam("id") int id) {
        ResponseDTO<Department> responseDTO = new ResponseDTO();
        departmentService.activeDepartmentById(id);
        responseDTO.setMessage(DepartmentSuccessMessage.ACTIVE_DEPARTMENT_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

}
