package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.DepartmentCreateDTO;
import com.fu.fuatsbe.DTO.DepartmentUpdateDTO;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.response.DepartmentResponse;

public interface DepartmentService {

    public List<DepartmentResponse> getAllDepartments();

    public DepartmentResponse getDepartmentById(int id);

    public List<DepartmentResponse> getDepartmentByName(String name);

    public DepartmentResponse updateDepartment(int id, DepartmentUpdateDTO departmentUpdateDTO);

    public Department createDepartment(DepartmentCreateDTO departmentCreateDTO);

    public boolean deleteDepartmentById(int id);
}
