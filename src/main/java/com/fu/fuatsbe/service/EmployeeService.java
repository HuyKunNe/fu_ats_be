package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.EmployeeUpdateDTO;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.response.EmployeeResponse;

public interface EmployeeService {

    public List<EmployeeResponse> getAllEmployees(int pageNo, int pageSize);

    public EmployeeResponse getEmployeeById(int id);

    public EmployeeResponse getEmployeeByCode(String code);

    public List<EmployeeResponse> getAllEmployeeByDepartment(int departmentId, int pageNo, int pageSize);

    public EmployeeResponse updateEmployee(int id, EmployeeUpdateDTO updateDTO);

    public Employee deleteEmployeeById(int id);

}
