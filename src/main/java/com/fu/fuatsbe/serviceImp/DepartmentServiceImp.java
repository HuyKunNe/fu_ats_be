package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.DepartmentCreateDTO;
import com.fu.fuatsbe.DTO.DepartmentUpdateDTO;
import com.fu.fuatsbe.constant.department.DepartmentErrorMessage;
import com.fu.fuatsbe.constant.department.DepartmentStatus;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.repository.DepartmentRepository;
import com.fu.fuatsbe.response.DepartmentResponse;
import com.fu.fuatsbe.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImp implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> list = departmentRepository.findAll();
        List<DepartmentResponse> result = new ArrayList<DepartmentResponse>();
        if (list.size() > 0) {
            for (Department department : list) {
                DepartmentResponse response = modelMapper.map(department, DepartmentResponse.class);
                result.add(response);
            }
        }
        return result;
    }

    @Override
    public DepartmentResponse getDepartmentById(int id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));
        DepartmentResponse response = modelMapper.map(department, DepartmentResponse.class);
        return response;
    }

    @Override
    public List<DepartmentResponse> getDepartmentByName(String name) {
        List<Department> list = departmentRepository.findByNameContaining(name);
        List<DepartmentResponse> result = new ArrayList<DepartmentResponse>();
        if (list.size() > 0) {
            for (Department department : list) {
                DepartmentResponse response = modelMapper.map(department, DepartmentResponse.class);
                result.add(response);
            }
        } else
            throw new IllegalStateException(DepartmentErrorMessage.DEPARTMENT_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public DepartmentResponse updateDepartment(int id, DepartmentUpdateDTO departmentUpdateDTO) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));
        department.setName(departmentUpdateDTO.getName());
        department.setRoom(departmentUpdateDTO.getRoom());
        department.setPhone(departmentUpdateDTO.getPhone());
        department.setStatus(departmentUpdateDTO.getStatus());
        departmentRepository.save(department);
        DepartmentResponse response = modelMapper.map(department, DepartmentResponse.class);
        return response;
    }

    @Override
    public Department createDepartment(DepartmentCreateDTO createDTO) {
        Optional<Department> optionalDepartment = departmentRepository.findDepartmentByName(createDTO.getName());
        if (optionalDepartment.isPresent())
            throw new IllegalStateException(DepartmentErrorMessage.DEPARTMENT_IS_EXIST_EXCEPTION);
        else {
            Department department = Department.builder().name(createDTO.getName()).phone(createDTO.getPhone())
                    .room(createDTO.getRoom()).status(DepartmentStatus.DEPARTMENT_ACTIVE).build();
            Department result = departmentRepository.save(department);
            return result;
        }
    }

    @Override
    public boolean deleteDepartmentById(int id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));
        department.setStatus(DepartmentStatus.DEPARTMENT_DISABLE);
        departmentRepository.save(department);
        return true;
    }

}
