package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.DTO.EmployeeCreateDTO;
import com.fu.fuatsbe.constant.employee.EmployeeSuccessMessage;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.dataformat.ListEmployeeData;
import com.fu.fuatsbe.response.EmployeeResponse;
import com.fu.fuatsbe.response.ListResponseDTO;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.service.EmployeeService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@CrossOrigin("*")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getEmployeeById(@PathVariable(name = "id") int employeeId) {
        ResponseDTO<EmployeeResponse> responseDTO = new ResponseDTO();
        EmployeeResponse employee = employeeService.getEmployeeById(employeeId);
        responseDTO.setData(employee);
        responseDTO.setSuccessMessage(EmployeeSuccessMessage.GET_EMPLOYEE_BY_ID_SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllEmployees")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public ResponseEntity<ListResponseDTO> getAllEmployees() {
        ListResponseDTO<EmployeeResponse> responseDTO = new ListResponseDTO();
        List<EmployeeResponse> list = employeeService.getAllEmployees();
        responseDTO.setData(list);
        responseDTO.setSuccessMessage(EmployeeSuccessMessage.GET_ALL_EMPLOYEE_SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/create")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public ResponseEntity<ListResponseDTO> createEmployeeByAdmin(@Validated @RequestBody EmployeeCreateDTO createDTO) {
        ListResponseDTO<EmployeeResponse> responseDTO = new ListResponseDTO();
        List<EmployeeResponse> list = employeeService.getAllEmployees();
        responseDTO.setData(list);
        responseDTO.setSuccessMessage(EmployeeSuccessMessage.GET_ALL_EMPLOYEE_SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

}
