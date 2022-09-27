package com.fu.fuatsbe.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fu.fuatsbe.DTO.DepartmentCreateDTO;
import com.fu.fuatsbe.DTO.DepartmentUpdateDTO;
import com.fu.fuatsbe.dataformat.DeleteData;
import com.fu.fuatsbe.dataformat.DepartmentData;
import com.fu.fuatsbe.dataformat.ListDepartmentData;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.repository.DepartmentRepository;
import com.fu.fuatsbe.response.DepartmentResponse;
import com.fu.fuatsbe.service.DepartmentService;
import com.fu.fuatsbe.service.EmployeeService;

@RestController
@RequestMapping
@CrossOrigin("*")
public class DepartmentController {
    private DepartmentRepository departmentRepository;
    private DepartmentService departmentService;
    private EmployeeService employeeService;

    public DepartmentController(DepartmentRepository departmentRepository, DepartmentService departmentService,
            EmployeeService employeeService) {
        this.departmentRepository = departmentRepository;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping("/department/getAll")
    public ListDepartmentData getAllRoles() {
        ListDepartmentData result = new ListDepartmentData();
        try {
            result.setData(departmentService.getAllDepartments());
            if (result.getData().isEmpty()) {
                result.setMessage("list is empty");
                result.setStatus("SUCCESS");
            } else {
                result.setMessage("get all departments is successfully");
                result.setStatus("SUCCESS");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus("ERROR");
        }
        return result;
    }

    @GetMapping("/department/getById/{id}")
    public DepartmentData getDepartmentById(@PathVariable int id) {
        DepartmentData result = new DepartmentData();
        try {
            result.setData(departmentService.getDepartmentById(id));
            if (result.getData() != null) {
                result.setMessage("find department by id successfully");
                result.setStatus("SUCCESS");
            } else {
                result.setMessage("department id is not exist");
                result.setStatus("SUCCESS");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus("ERROR");
        }
        return result;
    }

    @GetMapping("/department/getByName/{name}")
    public ListDepartmentData getDepartmentByName(@PathVariable String name) {
        ListDepartmentData result = new ListDepartmentData();
        try {
            result.setData(departmentService.getDepartmentByName(name));
            if (result.getData().isEmpty()) {
                result.setMessage("department is not exist");
                result.setStatus("SUCCESS");
            } else {
                result.setMessage("find department successfully");
                result.setStatus("SUCCESS");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus("ERROR");
        }
        return result;
    }

    @PutMapping("/department/edit/{id}")
    public DepartmentData updateDepartment(@PathVariable int id, @RequestBody DepartmentUpdateDTO updateDTO) {
        DepartmentData result = new DepartmentData();
        try {
            if (departmentRepository.existsById(id)) {
                DepartmentResponse response = departmentService.updateDepartment(id, updateDTO);
                if (response != null) {
                    result.setData(response);
                    result.setStatus("SUCCESS");
                    result.setMessage("department updated successfully");
                }
            } else {
                result.setMessage("department is not exist");
                result.setStatus("FAILURE");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus("ERROR");
        }
        return result;
    }

    @PostMapping("/department/create")
    public DepartmentData createDepartment(@RequestBody DepartmentCreateDTO createDTO) {
        DepartmentData result = new DepartmentData();
        try {
            Department department = departmentService.createDepartment(createDTO);
            DepartmentResponse response = new DepartmentResponse();
            if (department != null) {
                response.setId(department.getId());
                response.setName(department.getName());
                response.setRoom(department.getRoom());
                response.setPhone(department.getPhone());
                response.setStatus(department.getStatus());

                result.setMessage("create department successfully");
                result.setData(response);
                result.setStatus("SUCCESS");
            } else {
                result.setMessage("create department failure");
                result.setStatus("FAILURE");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus("ERROR");
        }
        return result;
    }

    @DeleteMapping("/department/delete/{id}")
    public DeleteData deleteDepartmnetById(@PathVariable int id) {
        DeleteData result = new DeleteData();
        try {
            if (departmentRepository.existsById(id)) {

                if (employeeService.getAllEmployeeByDepartment(id).isEmpty()) {
                    if (departmentService.deleteDepartmentById(id)) {
                        result.setMessage("delete department successfully");
                        result.setStatus("SUCCESS");
                    } else {
                        result.setMessage("delete department failure");
                        result.setStatus("FAILURE");
                    }
                } else {
                    result.setMessage("Can't delete because this department is still staffed");
                    result.setStatus("FAILURE");
                }

            } else {
                result.setMessage("department id is not exist");
                result.setStatus("FAILURE");
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus("ERROR");
        }
        return result;
    }

}
