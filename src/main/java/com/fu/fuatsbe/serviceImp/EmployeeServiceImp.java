package com.fu.fuatsbe.serviceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.EmployeeCreateDTO;
import com.fu.fuatsbe.DTO.EmployeeUpdateDTO;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.response.EmployeeResponse;
import com.fu.fuatsbe.service.EmployeeService;

@Service
public class EmployeeServiceImp implements EmployeeService{

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EmployeeResponse getEmployeeById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EmployeeResponse getEmployeeByCode(String code) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<EmployeeResponse> getAllEmployeeByDepartment(int departmentId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Employee createEmployee(EmployeeCreateDTO createDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EmployeeResponse updateEmployee(int id, EmployeeUpdateDTO updateDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteEmployeeById(int id) {
        // TODO Auto-generated method stub
        return false;
    }

    
}
