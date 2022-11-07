package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.EmployeeUpdateDTO;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.response.EmployeeResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

public interface EmployeeService {

    public ResponseWithTotalPage<EmployeeResponse> getAllEmployees(int pageNo, int pageSize);

    public EmployeeResponse getEmployeeById(int id);

    public EmployeeResponse getEmployeeByCode(String code);

    public ResponseWithTotalPage<EmployeeResponse> getAllEmployeeByDepartment(int departmentId, int pageNo,
            int pageSize);

    public EmployeeResponse updateEmployee(int id, EmployeeUpdateDTO updateDTO);

    public Employee deleteEmployeeById(int id);

}
