package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.EmployeeCreateDTO;
import com.fu.fuatsbe.DTO.EmployeeUpdateDTO;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.response.EmployeeResponse;
import com.fu.fuatsbe.service.EmployeeService;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImp(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

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
        List<Employee> list = employeeRepository.findEmployeesByDepartmentId(departmentId);
        List<EmployeeResponse> result = new ArrayList<EmployeeResponse>();
        if (list.size() > 0)
            for (Employee employee : list) {
                EmployeeResponse response = new EmployeeResponse();
                response.setId(employee.getId());
                response.setEmployeeCode(employee.getEmployeeCode());
                response.setName(employee.getName());
                response.setPhone(employee.getPhone());
                response.setAddress(employee.getAddress());
                response.setStatus(employee.getStatus());
                response.setDepartment(employee.getDepartment().getName());
                result.add(response);
            }
        return result;
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
