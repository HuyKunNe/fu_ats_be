package com.fu.fuatsbe.serviceImp;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;

import com.fu.fuatsbe.DTO.CountTotalDTO;
import com.fu.fuatsbe.response.NameAndCodeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.EmployeeUpdateDTO;
import com.fu.fuatsbe.constant.account.AccountErrorMessage;
import com.fu.fuatsbe.constant.account.AccountStatus;
import com.fu.fuatsbe.constant.department.DepartmentErrorMessage;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.employee.EmployeeStatus;
import com.fu.fuatsbe.constant.recruitmentRequest.RecruitmentRequestErrorMessage;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.repository.DepartmentRepository;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.RecruitmentRequestRepository;
import com.fu.fuatsbe.response.EmployeeResponse;
import com.fu.fuatsbe.response.IdAndNameResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RecruitmentRequestRepository recruitmentRequestRepository;
    private final DepartmentRepository departmentRepository;

    private final ModelMapper modelMapper;

    @Override
    public ResponseWithTotalPage<EmployeeResponse> getAllEmployees(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Employee> pageResult = employeeRepository.findAll(pageable);

        List<EmployeeResponse> list = new ArrayList<EmployeeResponse>();
        ResponseWithTotalPage<EmployeeResponse> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (Employee employee : pageResult.getContent()) {
                EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
                list.add(employeeResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(EmployeeErrorMessage.LIST_EMPTY_EXCEPTION);
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
    public ResponseWithTotalPage<EmployeeResponse> getAllEmployeeByDepartment(int departmentId, int pageNo,
            int pageSize) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Employee> pageResult = employeeRepository.findByDepartment(department, pageable);

        List<EmployeeResponse> list = new ArrayList<EmployeeResponse>();
        ResponseWithTotalPage<EmployeeResponse> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (Employee employee : pageResult.getContent()) {
                EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
                list.add(employeeResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(EmployeeErrorMessage.LIST_EMPTY_EXCEPTION);

        return result;
    }

    @Override
    public EmployeeResponse updateEmployee(int id, EmployeeUpdateDTO updateDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));

        employee.setImage(updateDTO.getImage());
        employee.setGender(updateDTO.getGender());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dob = simpleDateFormat.format(updateDTO.getDob());

        employee.setDob(Date.valueOf(dob));
        employee.setName(updateDTO.getName());
        employee.setPhone(updateDTO.getPhone());
        employee.setAddress(updateDTO.getAddress());

        Employee employeeSaved = employeeRepository.save(employee);
        EmployeeResponse response = modelMapper.map(employeeSaved, EmployeeResponse.class);
        return response;
    }

    @Override
    public Employee deleteEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
        if (employee.getAccount() != null) {
            if (employee.getAccount().getStatus().equals(AccountStatus.DISABLED)) {
                throw new NotFoundException(AccountErrorMessage.ACCOUNT_ALREADY_DELETED);
            }
            employee.getAccount().setStatus(AccountStatus.DISABLED);
            employee.setStatus(EmployeeStatus.DISABLE);
            Employee employeeSaved = employeeRepository.save(employee);
            return employeeSaved;
        }
        return null;
    }

    @Override
    public Employee activeEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
        if (employee.getAccount() != null) {
            employee.getAccount().setStatus(AccountStatus.ACTIVATED);
            employee.setStatus(EmployeeStatus.ACTIVATE);
            Employee employeeSaved = employeeRepository.save(employee);
            return employeeSaved;
        }
        return null;
    }

    @Override
    public List<IdAndNameResponse> getIdAndNameEmployeeByRequest(int requestId) {
        recruitmentRequestRepository.findById(requestId).orElseThrow(
                () -> new NotFoundException(RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));
        List<Tuple> tupleList = employeeRepository.getEmployeeByRequest(requestId);
        List<IdAndNameResponse> responses = new ArrayList<>();
        for (Tuple tuple : tupleList) {
            IdAndNameResponse response = IdAndNameResponse.builder()
                    .id(Integer.parseInt(tuple.get("id").toString()))
                    .name(tuple.get("name").toString())
                    .build();
            responses.add(response);
        }
        return responses;
    }

    @Override
    public CountTotalDTO countTotal() {
        Tuple tuple = employeeRepository.countTotal();
        CountTotalDTO count = CountTotalDTO.builder()
                .totalEmployee(Integer.parseInt(tuple.get("totalEmp").toString()))
                .totalDepartment(Integer.parseInt(tuple.get("totalDep").toString()))
                .totalPosition(Integer.parseInt(tuple.get("totalPos").toString()))
                .build();
        return count;
    }

    @Override
    public ResponseWithTotalPage<NameAndCodeResponse> getNameAndCodeByJobLevel(String level, int pageNo, int pageSize) {
        List<NameAndCodeResponse> nameAndCodeResponses = new ArrayList<>();
        ResponseWithTotalPage responseWithTotalPage = new ResponseWithTotalPage();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Tuple> pageResult = employeeRepository.getEmployeeByJobLevel(level, pageable);
        if(pageResult.hasContent()){
            for (Tuple tuple: pageResult.getContent()) {
                NameAndCodeResponse response = NameAndCodeResponse.builder()
                        .name(tuple.get("name").toString())
                        .code(tuple.get("employee_code").toString())
                        .build();
                nameAndCodeResponses.add(response);
            }
            responseWithTotalPage.setResponseList(nameAndCodeResponses);
            responseWithTotalPage.setTotalPage(pageResult.getTotalPages());
        }

        return responseWithTotalPage;
    }
}
