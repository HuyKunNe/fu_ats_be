package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fu.fuatsbe.response.IdAndNameResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.DepartmentCreateDTO;
import com.fu.fuatsbe.DTO.DepartmentUpdateDTO;
import com.fu.fuatsbe.constant.department.DepartmentErrorMessage;
import com.fu.fuatsbe.constant.department.DepartmentStatus;
import com.fu.fuatsbe.constant.employee.EmployeeStatus;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.exceptions.ExistException;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.exceptions.NotValidException;
import com.fu.fuatsbe.repository.DepartmentRepository;
import com.fu.fuatsbe.response.DepartmentResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.DepartmentService;

import lombok.RequiredArgsConstructor;

import javax.persistence.Tuple;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImp implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final ModelMapper modelMapper;

    @Override
    public ResponseWithTotalPage<DepartmentResponse> getAllDepartments(int pageNo, int pageSize, String name) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Department> pageResult = departmentRepository.findAll(pageable);

        ResponseWithTotalPage<DepartmentResponse> result = new ResponseWithTotalPage<>();
        List<DepartmentResponse> list = new ArrayList<>();
        if (pageResult.hasContent()) {
            for (Department department : pageResult.getContent()) {
                if (department.getName().toLowerCase().contains(name.toLowerCase())) {
                    DepartmentResponse response = modelMapper.map(department, DepartmentResponse.class);
                    list.add(response);
                }
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(DepartmentErrorMessage.LIST_DEPARTMENT_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public DepartmentResponse getDepartmentById(int id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));
        DepartmentResponse response = modelMapper.map(department, DepartmentResponse.class);
        return response;
    }

    @Override
    public ResponseWithTotalPage<DepartmentResponse> getDepartmentByName(String name, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Department> pageResult = departmentRepository.findByNameContaining(name, pageable);

        ResponseWithTotalPage<DepartmentResponse> result = new ResponseWithTotalPage<>();
        List<DepartmentResponse> list = new ArrayList<>();
        if (pageResult.hasContent()) {
            for (Department department : pageResult.getContent()) {
                DepartmentResponse response = modelMapper.map(department, DepartmentResponse.class);
                list.add(response);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(DepartmentErrorMessage.LIST_DEPARTMENT_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public DepartmentResponse updateDepartment(int id, DepartmentUpdateDTO departmentUpdateDTO) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new NotFoundException(
                DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));
        int totalDepartment = departmentRepository.totalDepartmentbyname(departmentUpdateDTO.getName(), id);

        if (totalDepartment > 0) {
            throw new NotValidException(DepartmentErrorMessage.DEPARTMENT_NAME_EXIST_EXCEPTION);
        }
        department.setName(departmentUpdateDTO.getName());
        department.setRoom(departmentUpdateDTO.getRoom());
        department.setPhone(departmentUpdateDTO.getPhone());
        departmentRepository.save(department);
        DepartmentResponse response = modelMapper.map(department, DepartmentResponse.class);
        return response;
    }

    @Override
    public Department createDepartment(DepartmentCreateDTO createDTO) {
        Optional<Department> optionalDepartment = departmentRepository.findDepartmentByName(createDTO.getName());
        if (optionalDepartment.isPresent())
            throw new ExistException(DepartmentErrorMessage.DEPARTMENT_IS_EXIST_EXCEPTION);
        else {
            Department department = Department.builder().name(createDTO.getName()).phone(createDTO.getPhone())
                    .room(createDTO.getRoom()).status(DepartmentStatus.DEPARTMENT_ACTIVE).build();
            Department result = departmentRepository.save(department);
            return result;
        }
    }

    @Override
    public boolean deleteDepartmentById(int id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new NotFoundException(
                DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));
        if (department != null) {
            for (Employee employee : department.getEmployees()) {
                if (employee.getStatus().equalsIgnoreCase(EmployeeStatus.ACTIVATE))
                    throw new ExistException(DepartmentErrorMessage.DEPARTMENT_ALREADY_HAVE_EMPLOYEE_EXCEPTION);
            }
            department.setStatus(DepartmentStatus.DEPARTMENT_DISABLE);
            departmentRepository.save(department);
        } else
            throw new NotFoundException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION);
        return true;
    }

    @Override
    public boolean activeDepartmentById(int id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new NotFoundException(
                DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));
        if (department != null) {
            department.setStatus(DepartmentStatus.DEPARTMENT_ACTIVE);
            departmentRepository.save(department);
        } else
            throw new NotFoundException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION);
        return true;
    }

    @Override
    public List<IdAndNameResponse> getDepartmentName() {
        List<Tuple> tupleList = departmentRepository.getIdAndNameDepartment();
        if (tupleList.size() <= 0) {
            throw new NotFoundException(DepartmentErrorMessage.LIST_DEPARTMENT_EMPTY_EXCEPTION);
        }
        List<IdAndNameResponse> responseList = new ArrayList<>();
        for (Tuple tuple : tupleList) {
            IdAndNameResponse response = IdAndNameResponse.builder()
                    .id(Integer.parseInt(tuple.get("id").toString()))
                    .name(tuple.get("name").toString())
                    .build();
            responseList.add(response);
        }
        return responseList;
    }

}
