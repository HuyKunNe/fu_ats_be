package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.DepartmentCreateDTO;
import com.fu.fuatsbe.DTO.DepartmentUpdateDTO;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.repository.DepartmentRepository;
import com.fu.fuatsbe.response.DepartmentResponse;
import com.fu.fuatsbe.service.DepartmentService;

@Service
public class DepartmentServiceImp implements DepartmentService {

    private DepartmentRepository departmentRepository;

    public DepartmentServiceImp(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> list = departmentRepository.findAll();
        List<DepartmentResponse> result = new ArrayList<DepartmentResponse>();
        if (list.size() > 0)
            for (Department department : list) {
                DepartmentResponse response = new DepartmentResponse();
                response.setId(department.getId());
                response.setRoom(department.getRoom());
                response.setName(department.getName());
                response.setPhone(department.getPhone());
                response.setStatus(department.getStatus());
                result.add(response);
            }
        return result;
    }

    @Override
    public DepartmentResponse getDepartmentById(int id) {
        DepartmentResponse result = new DepartmentResponse();
        if (departmentRepository.existsById(id)) {
            Department department = departmentRepository.findById(id).get();
            result.setId(id);
            result.setRoom(department.getRoom());
            result.setName(department.getName());
            result.setPhone(department.getPhone());
            result.setStatus(department.getStatus());
        } else
            result = null;
        return result;
    }

    @Override
    public List<DepartmentResponse> getDepartmentByName(String name) {
        List<Department> list = departmentRepository.findByNameContaining(name);
        List<DepartmentResponse> result = new ArrayList<DepartmentResponse>();
        if (list.size() > 0)
            for (Department department : list) {
                DepartmentResponse response = new DepartmentResponse();
                response.setId(department.getId());
                response.setRoom(department.getRoom());
                response.setName(department.getName());
                response.setPhone(department.getPhone());
                response.setStatus(department.getStatus());
                result.add(response);
            }
        return result;
    }

    @Override
    public DepartmentResponse updateDepartment(int id, DepartmentUpdateDTO departmentUpdateDTO) {
        DepartmentResponse result = new DepartmentResponse();
        Department department = departmentRepository.findById(id).get();
        department.setName(departmentUpdateDTO.getName());
        department.setRoom(departmentUpdateDTO.getRoom());
        department.setPhone(departmentUpdateDTO.getPhone());
        department.setStatus(departmentUpdateDTO.getStatus());
        departmentRepository.save(department);
        result.setId(id);
        result.setName(department.getName());
        result.setRoom(department.getRoom());
        result.setPhone(department.getPhone());
        result.setStatus(department.getStatus());
        return result;
    }

    @Override
    public Department createDepartment(DepartmentCreateDTO createDTO) {
        Department department = new Department();
        department.setName(createDTO.getName());
        department.setRoom(createDTO.getRoom());
        department.setPhone(createDTO.getPhone());
        department.setStatus(0);
        return departmentRepository.save(department);
    }

    @Override
    public boolean deleteDepartmentById(int id) {
        boolean result = false;
        if (departmentRepository.deleteDepartmentById(id) != 0) {
            result = true;
        }
        return result;
    }

}
