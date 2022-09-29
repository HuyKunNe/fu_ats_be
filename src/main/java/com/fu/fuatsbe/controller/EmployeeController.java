package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.DTO.ResponseDTO;
import com.fu.fuatsbe.constant.employee.EmployeeSuccessMessage;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.response.EmployeeResponse;
import com.fu.fuatsbe.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@CrossOrigin("*")
@RequiredArgsConstructor
public class EmployeeController {

    private  final EmployeeService employeeService;

    @GetMapping("/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public ResponseEntity<ResponseDTO> getEmployeeById(@PathVariable(name = "id") int employeeId){
            ResponseDTO<EmployeeResponse> responseDTO = new ResponseDTO();
            EmployeeResponse employee = employeeService.getEmployeeById(employeeId);
            responseDTO.setData(employee);
            responseDTO.setSuccessMessage(EmployeeSuccessMessage.GET_EMPLOYEE_BY_ID_SUCCESS);
            return ResponseEntity.ok().body(responseDTO);
    }

}
