package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.CountTotalDTO;
import com.fu.fuatsbe.DTO.EmployeeUpdateDTO;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.response.EmployeeResponse;
import com.fu.fuatsbe.response.IdAndNameResponse;
import com.fu.fuatsbe.response.NameAndCodeResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

import java.util.List;

public interface EmployeeService {

    public ResponseWithTotalPage<EmployeeResponse> getAllEmployees(int pageNo, int pageSize);

    public EmployeeResponse getEmployeeById(int id);

    public ResponseWithTotalPage<EmployeeResponse> getAllEmployeeByDepartment(int departmentId, int pageNo,
            int pageSize);

    public EmployeeResponse updateEmployee(int id, EmployeeUpdateDTO updateDTO);

    public Employee deleteEmployeeById(int id);

    Employee activeEmployeeById(int id);

    List<IdAndNameResponse> getIdAndNameEmployeeByRequest(int requestId);

    CountTotalDTO countTotal();
    ResponseWithTotalPage<NameAndCodeResponse> getNameAndCodeByJobLevel(String level, int pageNo, int pageSize);

}
