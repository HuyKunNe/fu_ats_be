package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.DepartmentCreateDTO;
import com.fu.fuatsbe.DTO.DepartmentUpdateDTO;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.response.DepartmentResponse;

public interface DepartmentService {

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse getDepartmentById(int id);

    List<DepartmentResponse> getDepartmentByName(String name);

    DepartmentResponse updateDepartment(int id, DepartmentUpdateDTO departmentUpdateDTO);

    Department createDepartment(DepartmentCreateDTO departmentCreateDTO);

    boolean deleteDepartmentById(int id);
}
