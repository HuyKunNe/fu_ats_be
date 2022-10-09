package com.fu.fuatsbe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fu.fuatsbe.DTO.RecruitmentRequestCreateDTO;
import com.fu.fuatsbe.DTO.RecruitmentRequestUpdateDTO;
import com.fu.fuatsbe.constant.recruitmentRequest.RecruitmentRequestSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.ListResponseDTO;
import com.fu.fuatsbe.response.RecruitmentRequestResponse;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.service.RecruitmentRequestService;

import lombok.RequiredArgsConstructor;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/recruitmentRequest")
@CrossOrigin("*")
@RequiredArgsConstructor
public class RecruitmentRequestController {

    private final RecruitmentRequestService recruitmentRequestService;

    @GetMapping("/getAll")
    @PermitAll
    public ResponseEntity<ListResponseDTO> getAllRecruitmentRequests() {

        ListResponseDTO<RecruitmentRequestResponse> response = new ListResponseDTO();
        List<RecruitmentRequestResponse> list = recruitmentRequestService.getAllRecruitmentRequests();
        response.setData(list);
        response.setMessage(RecruitmentRequestSuccessMessage.GET_ALL_RECRUITMENT_REQUEST_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getRecruitmentRequestById(@RequestParam("id") int id) {
        ResponseDTO<RecruitmentRequestResponse> responseDTO = new ResponseDTO();
        RecruitmentRequestResponse positionResponse = recruitmentRequestService.getRecruitmentRequestById(id);
        responseDTO.setData(positionResponse);
        responseDTO.setMessage(RecruitmentRequestSuccessMessage.GET_RECRUITMENT_REQUEST_BY_ID_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getOpenRecruitmentRequest")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllOpenRecruitmentRequest() {
        ListResponseDTO<RecruitmentRequestResponse> response = new ListResponseDTO();
        List<RecruitmentRequestResponse> list = recruitmentRequestService.getAllOpenRecruitmentRequest();
        response.setData(list);
        response.setMessage(RecruitmentRequestSuccessMessage.GET_OPEN_RECRUITMENT_PLAN_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getFilledRecruitmentRequest")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllFilledRecruitmentRequest() {
        ListResponseDTO<RecruitmentRequestResponse> response = new ListResponseDTO();
        List<RecruitmentRequestResponse> list = recruitmentRequestService.getAllFilledRecruitmentRequest();
        response.setData(list);
        response.setMessage(RecruitmentRequestSuccessMessage.GET_FILLED_RECRUITMENT_REQUEST_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getClosedRecruitmentRequest")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllClosedRecruitmentRequest() {
        ListResponseDTO<RecruitmentRequestResponse> response = new ListResponseDTO();
        List<RecruitmentRequestResponse> list = recruitmentRequestService.getAllClosedRecruitmentRequest();
        response.setData(list);
        response.setMessage(RecruitmentRequestSuccessMessage.GET_CLOSED_RECRUITMENT_REQUEST_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getByCreator")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllRecruitmentRequestByCreator(@RequestParam("id") int id) {
        ListResponseDTO<RecruitmentRequestResponse> response = new ListResponseDTO();
        List<RecruitmentRequestResponse> list = recruitmentRequestService.getAllRecruitmentRequestByCreator(id);
        response.setData(list);
        response.setMessage(RecruitmentRequestSuccessMessage.GET_CLOSED_RECRUITMENT_REQUEST_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> createRecruitmentRequest(@RequestBody RecruitmentRequestCreateDTO createDTO) {
        ResponseDTO<RecruitmentRequestResponse> responseDTO = new ResponseDTO();
        RecruitmentRequestResponse recruitmentRequest = recruitmentRequestService.createRecruitmentRequest(createDTO);
        responseDTO.setData(recruitmentRequest);
        responseDTO.setMessage(RecruitmentRequestSuccessMessage.CREATE_RECRUITMENT_REQUEST_SUCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> updateRecruitmentRequest(@RequestParam("id") int id,
            @RequestBody RecruitmentRequestUpdateDTO updateDTO) {
        ResponseDTO<RecruitmentRequestResponse> responseDTO = new ResponseDTO();
        RecruitmentRequestResponse planDetailDTO = recruitmentRequestService.updateRecruitmentRequest(id, updateDTO);
        responseDTO.setData(planDetailDTO);
        responseDTO.setMessage(RecruitmentRequestSuccessMessage.UPDATE_RECRUITMENT_REQUEST_SUCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/closeRecruitmentRequest/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> closeRecruitmentRequest(@RequestParam("id") int id) {
        ResponseDTO<RecruitmentRequestResponse> responseDTO = new ResponseDTO();
        RecruitmentRequestResponse planDetailDTO = recruitmentRequestService.closeRecruitmentRequest(id);
        responseDTO.setData(planDetailDTO);
        responseDTO.setMessage(RecruitmentRequestSuccessMessage.UPDATE_RECRUITMENT_REQUEST_SUCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

}
