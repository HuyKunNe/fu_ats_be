package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.PositionCreateDTO;
import com.fu.fuatsbe.DTO.PositionUpdateDTO;
import com.fu.fuatsbe.constant.department.DepartmentErrorMessage;
import com.fu.fuatsbe.constant.department.DepartmentStatus;
import com.fu.fuatsbe.constant.employee.EmployeeStatus;
import com.fu.fuatsbe.constant.postion.PositionErrorMessage;
import com.fu.fuatsbe.constant.postion.PositionStatus;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.Position;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.exceptions.NotValidException;
import com.fu.fuatsbe.repository.DepartmentRepository;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.PositionRepository;
import com.fu.fuatsbe.response.PositionResponse;
import com.fu.fuatsbe.service.PositionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PositionServiceImp implements PositionService {

    private final ModelMapper modelMapper;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<PositionResponse> getAllPositions() {
        List<Position> list = positionRepository.findAll();
        List<PositionResponse> result = new ArrayList<PositionResponse>();
        if (list.size() > 0) {
            for (Position position : list) {
                PositionResponse response = modelMapper.map(position, PositionResponse.class);
                result.add(response);
            }
        } else
            throw new ListEmptyException(PositionErrorMessage.LIST_POSITION_EMPTY);
        return result;
    }

    @Override
    public PositionResponse getPosionById(int id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST));
        PositionResponse response = modelMapper.map(position, PositionResponse.class);
        return response;
    }

    @Override
    public PositionResponse createPosition(PositionCreateDTO createDTO) {
        Optional<Department> optionalDepartment = departmentRepository.findById(createDTO.getDepartmentId());
        if (optionalDepartment.isPresent()) {
            if (!optionalDepartment.get().getStatus().equalsIgnoreCase(DepartmentStatus.DEPARTMENT_ACTIVE))
                throw new NotValidException(DepartmentErrorMessage.DEPARTMENT_UNAVAIABLE_EXCEPTION);
            Position position = Position.builder().name(createDTO.getName()).department(optionalDepartment.get())
                    .status(PositionStatus.ACTIVATE).build();
            positionRepository.save(position);
            PositionResponse response = modelMapper.map(position, PositionResponse.class);
            return response;
        } else {
            throw new NotFoundException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    public PositionResponse updatePosition(int id, PositionUpdateDTO updateDTO) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST));
        Department department = departmentRepository.findById(updateDTO.getDepartmentId())
                .orElseThrow(() -> new NotFoundException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));
        if (department.getStatus().equalsIgnoreCase(DepartmentStatus.DEPARTMENT_ACTIVE)) {
            position.setName(updateDTO.getName());
            position.setDepartment(department);
            Position positionSaved = positionRepository.save(position);
            PositionResponse response = modelMapper.map(positionSaved, PositionResponse.class);
            return response;
        } else
            throw new NotValidException(DepartmentErrorMessage.DEPARTMENT_UNAVAIABLE_EXCEPTION);
    }

    @Override
    public Position deletePosition(int id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST));
        List<Employee> list = employeeRepository.findbyPositionIdAndStatus(id, EmployeeStatus.ACTIVATE);
        if (list == null) {
            position.setStatus(PositionStatus.DISABLE);
            Position positionSaved = positionRepository.save(position);
            return positionSaved;
        } else
            throw new NotValidException("Employee already has this Position");
    }

    @Override
    public List<PositionResponse> getPositionByDepartment(int id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));
        List<Position> list = positionRepository.findByDepartment(department);
        List<PositionResponse> result = new ArrayList<PositionResponse>();
        if (list.size() > 0) {
            for (Position position : list) {
                PositionResponse response = modelMapper.map(position, PositionResponse.class);
                result.add(response);
            }
        } else
            throw new ListEmptyException(PositionErrorMessage.LIST_POSITION_EMPTY);
        return result;
    }
}
