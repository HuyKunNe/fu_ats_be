package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import com.fu.fuatsbe.constant.employee.EmployeeStatus;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.exceptions.ExistException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.PositionCreateDTO;
import com.fu.fuatsbe.DTO.PositionUpdateDTO;
import com.fu.fuatsbe.constant.department.DepartmentErrorMessage;
import com.fu.fuatsbe.constant.department.DepartmentStatus;
import com.fu.fuatsbe.constant.postion.PositionErrorMessage;
import com.fu.fuatsbe.constant.postion.PositionStatus;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.entity.Position;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.exceptions.NotValidException;
import com.fu.fuatsbe.repository.DepartmentRepository;
import com.fu.fuatsbe.repository.PositionRepository;
import com.fu.fuatsbe.response.IdAndNameResponse;
import com.fu.fuatsbe.response.PositionResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.PositionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PositionServiceImp implements PositionService {

    private final ModelMapper modelMapper;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public ResponseWithTotalPage<PositionResponse> getAllPositions(int pageNo, int pageSize, String search) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Position> pageResult = positionRepository.findAll(pageable);
        List<PositionResponse> list = new ArrayList<PositionResponse>();
        ResponseWithTotalPage<PositionResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (Position position : pageResult.getContent()) {
                if (position.getName().toLowerCase().contains(search.toLowerCase())) {
                    int total = positionRepository.countUsedPosition(position.getId());
                    PositionResponse response = modelMapper.map(position, PositionResponse.class);
                    response.setNumberUsePosition(total);
                    list.add(response);
                }
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
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
    public void deletePosition(int id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST));
        if (position != null) {
            for (Employee employee : position.getEmployees()) {
                if (employee.getStatus().equalsIgnoreCase(EmployeeStatus.ACTIVATE))
                    throw new ExistException(PositionErrorMessage.POSITION_DISABLE_ERROR);
            }
            position.setStatus(PositionStatus.DISABLE);
            positionRepository.save(position);
        }

    }

    @Override
    public void activePosition(int id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST));

        position.setStatus(PositionStatus.ACTIVATE);
        positionRepository.save(position);
    }

    @Override
    public ResponseWithTotalPage<PositionResponse> getPositionByDepartment(int id, int pageNo, int pageSize) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Position> pageResult = positionRepository.findByDepartment(department, pageable);
        List<PositionResponse> list = new ArrayList<PositionResponse>();
        ResponseWithTotalPage<PositionResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (Position position : pageResult.getContent()) {
                PositionResponse response = modelMapper.map(position, PositionResponse.class);
                list.add(response);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(PositionErrorMessage.LIST_POSITION_EMPTY);
        return result;
    }

    @Override
    public List<IdAndNameResponse> getPositionIdAndName(String departmentName) {
        Department department = departmentRepository.findDepartmentByName(departmentName)
                .orElseThrow(() -> new NotFoundException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));
        List<Tuple> tupleList = positionRepository.getPositionIdAndName(department.getId());
        if (tupleList.size() <= 0) {
            throw new NotFoundException(PositionErrorMessage.LIST_POSITION_EMPTY);
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
