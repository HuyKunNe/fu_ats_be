package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;

import com.fu.fuatsbe.constant.account.AccountErrorMessage;
import com.fu.fuatsbe.constant.account.AccountStatus;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.EmployeeUpdateDTO;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.response.EmployeeResponse;
import com.fu.fuatsbe.service.EmployeeService;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> list = employeeRepository.findAll();
        List<EmployeeResponse> result = new ArrayList<EmployeeResponse>();
        if (list.size() > 0) {
            for (Employee employee : list) {
                EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
                result.add(employeeResponse);
            }
        }
        return result;
    }

    @Override
    public EmployeeResponse getEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
        EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
        return employeeResponse;
    }

    @Override
    public EmployeeResponse getEmployeeByCode(String code) {
        Employee employee = employeeRepository.findByEmployeeCode(code)
                .orElseThrow(() -> new IllegalStateException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
        EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
        return employeeResponse;
    }

    @Override
    public List<EmployeeResponse> getAllEmployeeByDepartment(int departmentId) {
        List<Employee> list = employeeRepository.findEmployeesByDepartmentId(departmentId);
        List<EmployeeResponse> result = new ArrayList<EmployeeResponse>();
        if (list.size() > 0)
            for (Employee employee : list) {
                EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
                result.add(employeeResponse);
            }
        return result;
    }

    @Override
    public EmployeeResponse updateEmployee(int id, EmployeeUpdateDTO updateDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Employee deleteEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
        if (employee.getAccount() != null) {
            if (employee.getAccount().getStatus().equals(AccountStatus.DISABLED)) {
                throw new IllegalStateException(AccountErrorMessage.ACCOUNT_ALREADY_DELETED);
            }
            employee.getAccount().setStatus(AccountStatus.DISABLED);
            Employee employeeSaved = employeeRepository.save(employee);
            return employeeSaved;
        }
        return null;
    }

}
