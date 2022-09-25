package com.fu.fuatsbe.serviceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.DepartmentCreateDTO;
import com.fu.fuatsbe.DTO.DepartmentUpdateDTO;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.response.DepartmentResponse;
import com.fu.fuatsbe.service.DepartmentService;

@Service
public class DepartmentServiceImp implements DepartmentService{

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DepartmentResponse getDepartmentById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DepartmentResponse getDepartmentByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DepartmentResponse updateDepartment(int id, DepartmentUpdateDTO departmentUpdateDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Department createDepartment(DepartmentCreateDTO departmentCreateDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteDepartmentById(int id) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
