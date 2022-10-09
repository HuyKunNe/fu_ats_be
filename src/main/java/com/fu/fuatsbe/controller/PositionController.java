package com.fu.fuatsbe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fu.fuatsbe.DTO.PositionCreateDTO;
import com.fu.fuatsbe.DTO.PositionUpdateDTO;
import com.fu.fuatsbe.constant.postion.PositionSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.entity.Position;
import com.fu.fuatsbe.response.ListResponseDTO;
import com.fu.fuatsbe.response.PositionResponse;
import com.fu.fuatsbe.response.ResponseDTO;
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
    public ResponseEntity<ListResponseDTO> getAllPositions() {

        ListResponseDTO<PositionResponse> response = new ListResponseDTO();
        List<PositionResponse> list = positionService.getAllPositions();
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
        ResponseDTO<Boolean> responseDTO = new ResponseDTO();
        Position position = positionService.deletePosition(id);
        responseDTO.setData(true);
        responseDTO.setMessage(PositionSuccessMessage.DELETE_POSITION);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

}
