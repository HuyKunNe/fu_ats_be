package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.DepartmentCreateDTO;
import com.fu.fuatsbe.DTO.DepartmentUpdateDTO;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.response.DepartmentResponse;
import com.fu.fuatsbe.response.IdAndNameResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

import java.util.List;

public interface DepartmentService {

    public ResponseWithTotalPage<DepartmentResponse> getAllDepartments(int pageNo, int pageSize, String name);

    public DepartmentResponse getDepartmentById(int id);

    public ResponseWithTotalPage<DepartmentResponse> getDepartmentByName(String name, int pageNo, int pageSize);

    public DepartmentResponse updateDepartment(int id, DepartmentUpdateDTO departmentUpdateDTO);

    public Department createDepartment(DepartmentCreateDTO departmentCreateDTO);

    public boolean deleteDepartmentById(int id);
     boolean activeDepartmentById(int id);
    List<IdAndNameResponse> getDepartmentName();
}
