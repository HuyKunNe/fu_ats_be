package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.EmployeeCreateDTO;
import com.fu.fuatsbe.DTO.EmployeeUpdateDTO;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.response.EmployeeResponse;

public interface EmployeeService {

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse getEmployeeById(int id);

    EmployeeResponse getEmployeeByCode(String code);

    List<EmployeeResponse> getAllEmployeeByDepartment(int departmentId);

    Employee createEmployee(EmployeeCreateDTO createDTO);

    EmployeeResponse updateEmployee(int id, EmployeeUpdateDTO updateDTO);

    boolean deleteEmployeeById(int id);
}
