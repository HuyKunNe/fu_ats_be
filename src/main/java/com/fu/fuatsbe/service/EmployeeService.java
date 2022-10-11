package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.EmployeeUpdateDTO;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.response.EmployeeResponse;

public interface EmployeeService {

    List<EmployeeResponse> getAllEmployees(int pageNo, int pageSize);

    EmployeeResponse getEmployeeById(int id);

    EmployeeResponse getEmployeeByCode(String code);

    List<EmployeeResponse> getAllEmployeeByDepartment(int departmentId, int pageNo, int pageSize);

    EmployeeResponse updateEmployee(int id, EmployeeUpdateDTO updateDTO);

    Employee deleteEmployeeById(int id);

}
