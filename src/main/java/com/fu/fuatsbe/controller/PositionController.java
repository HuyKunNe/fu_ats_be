package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.constant.department.DepartmentSuccessMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fu.fuatsbe.DTO.PositionCreateDTO;
import com.fu.fuatsbe.DTO.PositionUpdateDTO;
import com.fu.fuatsbe.constant.postion.PositionSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.entity.Position;
import com.fu.fuatsbe.response.PositionResponse;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.PositionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/position")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping("/getAll")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllPositions(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "") String name) {

        ResponseDTO<ResponseWithTotalPage> response = new ResponseDTO();
        ResponseWithTotalPage<PositionResponse> list = positionService.getAllPositions(pageNo, pageSize, name);
        response.setData(list);
        response.setMessage(PositionSuccessMessage.GET_ALL_POSITION_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getPositionById(@RequestParam("id") int id) {
        ResponseDTO<PositionResponse> responseDTO = new ResponseDTO();
        PositionResponse positionResponse = positionService.getPosionById(id);
        responseDTO.setData(positionResponse);
        responseDTO.setMessage(PositionSuccessMessage.GET_POSITION_BY_ID);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/create")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> createPosition(@RequestBody PositionCreateDTO createDTO) {
        ResponseDTO<PositionResponse> responseDTO = new ResponseDTO();
        PositionResponse positionResponse = positionService.createPosition(createDTO);
        responseDTO.setData(positionResponse);
        responseDTO.setMessage(PositionSuccessMessage.CREATE_POSITION);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> editPosition(@RequestParam("id") int id,
            @RequestBody PositionUpdateDTO updateDTO) {
        ResponseDTO<PositionResponse> responseDTO = new ResponseDTO();
        PositionResponse positionResponse = positionService.updatePosition(id, updateDTO);
        responseDTO.setData(positionResponse);
        responseDTO.setMessage(PositionSuccessMessage.UPDATE_POSITION);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public ResponseEntity<ResponseDTO> deletePosition(@RequestParam("id") int id) {
        ResponseDTO responseDTO = new ResponseDTO();
        positionService.deletePosition(id);
        responseDTO.setMessage(PositionSuccessMessage.DELETE_POSITION);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PatchMapping("/active/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN)
    public ResponseEntity<ResponseDTO> activePosition(@RequestParam("id") int id) {
        ResponseDTO responseDTO = new ResponseDTO();
        positionService.activePosition(id);
        responseDTO.setMessage(PositionSuccessMessage.ACTIVE_POSITION);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getPositionByDepartment")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getPositionById(@RequestParam int id,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> response = new ResponseDTO();
        ResponseWithTotalPage<PositionResponse> list = positionService.getPositionByDepartment(id, pageNo, pageSize);
        response.setData(list);
        response.setMessage(PositionSuccessMessage.GET_POSITION_BY_DEPARTMENT);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getIdName")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getPositionIdAndName(@RequestParam String departmentName) {
        ResponseDTO response = new ResponseDTO();
        response.setData(positionService.getPositionIdAndName(departmentName));
        response.setMessage(PositionSuccessMessage.GET_POSITION_ID_NAME);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getPositionNameByDepartment")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getPositionNameByDepartment(@RequestParam int id) {
        ResponseDTO response = new ResponseDTO();
        response.setData(positionService.getPositionNameByDepartment(id));
        response.setMessage(PositionSuccessMessage.GET_POSITION_ID_NAME);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

}
