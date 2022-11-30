package com.fu.fuatsbe.controller;

import java.util.List;

import com.fu.fuatsbe.DTO.*;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
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

import com.fu.fuatsbe.constant.recruitmentRequest.RecruitmentRequestSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.IdAndNameResponse;
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
    public ResponseEntity<ResponseDTO> getAllRecruitmentRequests(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        ResponseDTO response = new ResponseDTO();
        ResponseWithTotalPage responseWithTotalPages = recruitmentRequestService
                .getAllRecruitmentRequests(pageNo, pageSize);
        response.setData(responseWithTotalPages);
        response.setMessage(RecruitmentRequestSuccessMessage.GET_ALL_RECRUITMENT_REQUEST_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getById")
    @PermitAll
    public ResponseEntity<ResponseDTO> getRecruitmentRequestById(@RequestParam("id") int id) {
        ResponseDTO<RecruitmentRequestResponse> responseDTO = new ResponseDTO();
        RecruitmentRequestResponse positionResponse = recruitmentRequestService.getRecruitmentRequestById(id);
        responseDTO.setData(positionResponse);
        responseDTO.setMessage(RecruitmentRequestSuccessMessage.GET_RECRUITMENT_REQUEST_BY_ID_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getOpenRecruitmentRequest")
    @PermitAll
    public ResponseEntity<ResponseDTO> getAllOpenRecruitmentRequest(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO response = new ResponseDTO();
        ResponseWithTotalPage responseWithTotalPages = recruitmentRequestService
                .getAllOpenRecruitmentRequest(pageNo, pageSize);
        response.setData(responseWithTotalPages);
        response.setMessage(RecruitmentRequestSuccessMessage.GET_OPEN_RECRUITMENT_PLAN_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getFilledRecruitmentRequest")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllFilledRecruitmentRequest(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO response = new ResponseDTO();
        ResponseWithTotalPage responseWithTotalPages = recruitmentRequestService
                .getAllFilledRecruitmentRequest(pageNo, pageSize);
        response.setData(responseWithTotalPages);
        response.setMessage(RecruitmentRequestSuccessMessage.GET_FILLED_RECRUITMENT_REQUEST_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getClosedRecruitmentRequest")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllClosedRecruitmentRequest(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO response = new ResponseDTO();
        ResponseWithTotalPage responseWithTotalPages = recruitmentRequestService
                .getAllClosedRecruitmentRequest(pageNo, pageSize);
        response.setData(responseWithTotalPages);
        response.setMessage(RecruitmentRequestSuccessMessage.GET_CLOSED_RECRUITMENT_REQUEST_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getByCreator")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllRecruitmentRequestByCreator(@RequestParam("id") int id,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO response = new ResponseDTO();
        ResponseWithTotalPage responseWithTotalPages = recruitmentRequestService
                .getAllRecruitmentRequestByCreator(id, pageNo, pageSize);
        response.setData(responseWithTotalPages);
        response.setMessage(RecruitmentRequestSuccessMessage.GET_ALL_RECRUITMENT_REQUEST_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> createRecruitmentRequest(@RequestBody RecruitmentRequestCreateDTO createDTO) {
        ResponseDTO<RecruitmentRequestResponse> responseDTO = new ResponseDTO();
        RecruitmentRequestResponse recruitmentRequest = recruitmentRequestService.createRecruitmentRequest(createDTO);
        responseDTO.setData(recruitmentRequest);
        responseDTO.setMessage(RecruitmentRequestSuccessMessage.CREATE_RECRUITMENT_REQUEST_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> updateRecruitmentRequest(@RequestParam("id") int id,
            @RequestBody RecruitmentRequestUpdateDTO updateDTO) {
        ResponseDTO<RecruitmentRequestResponse> responseDTO = new ResponseDTO();
        RecruitmentRequestResponse planDetailDTO = recruitmentRequestService.updateRecruitmentRequest(id, updateDTO);
        responseDTO.setData(planDetailDTO);
        responseDTO.setMessage(RecruitmentRequestSuccessMessage.UPDATE_RECRUITMENT_REQUEST_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/closeRecruitmentRequest/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> closeRecruitmentRequest(@RequestParam("id") int id) {
        ResponseDTO<RecruitmentRequestResponse> responseDTO = new ResponseDTO();
        RecruitmentRequestResponse planDetailDTO = recruitmentRequestService.closeRecruitmentRequest(id);
        responseDTO.setData(planDetailDTO);
        responseDTO.setMessage(RecruitmentRequestSuccessMessage.CLOSED_RECRUITMENT_REQUEST_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/searchRecruitmentRequest")
    @PermitAll
    public ResponseEntity<ListResponseDTO> searchRecruitmentRequest(
            @RequestBody RecruitmentRequestSearchDTO searchDTO) {
        ListResponseDTO<RecruitmentRequestResponse> response = new ListResponseDTO();
        List<RecruitmentRequestResponse> list = recruitmentRequestService.searchRecruitmentRequest(searchDTO);
        response.setData(list);
        response.setMessage(RecruitmentRequestSuccessMessage.SEARCH_RECRUITMENT_REQUEST_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @PermitAll
    @GetMapping("/getCategory")
    public ResponseEntity<ResponseDTO> getCategory() {
        ResponseDTO responseDTO = new ResponseDTO();
        RecruitmentSearchCategoryDTO recruitmentSearchCategoryDTO = recruitmentRequestService.searchCategory();
        responseDTO.setMessage(RecruitmentRequestSuccessMessage.GET_SEARCH_CATEGORY_SUCCESS);
        responseDTO.setData(recruitmentSearchCategoryDTO);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getNewestRecruitmentRequest")
    @PermitAll
    public ResponseEntity<ListResponseDTO> getNewestRecruitmentRequest() {
        ListResponseDTO<RecruitmentRequestResponse> response = new ListResponseDTO();
        List<RecruitmentRequestResponse> list = recruitmentRequestService.getNewestRecruitmentRequest();
        response.setData(list);
        response.setMessage(RecruitmentRequestSuccessMessage.SEARCH_RECRUITMENT_REQUEST_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getIdAndNameByDepartment")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getIdAndNameByDepartment(@RequestParam("department_id") int departmentId) {
        ResponseDTO response = new ResponseDTO();
        response.setData(recruitmentRequestService.getIdAndNameRequestByDepartment(departmentId));
        response.setStatus(ResponseStatusDTO.SUCCESS);
        response.setMessage(RecruitmentRequestSuccessMessage.GET_ID_NAME_BY_DEPARTMENT);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getAllActiveRequest")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllActiveRequest() {
        ResponseDTO responseDTO = new ResponseDTO();
        List<IdAndNameResponse> responseList = recruitmentRequestService.getAllActiveRequest();
        responseDTO.setData(responseList);
        responseDTO.setMessage(RecruitmentRequestSuccessMessage.GET_ACTIVE_REQUEST_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllExpiredRecruitmentRequest")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllExpiredRecruitmentRequest(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO response = new ResponseDTO();
        ResponseWithTotalPage responseWithTotalPages = recruitmentRequestService.getExpiryDateRecruitmentRequest(pageNo,
                pageSize);
        response.setData(responseWithTotalPages);
        response.setMessage(RecruitmentRequestSuccessMessage.GET_EXPIRY_REQUEST_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }
}
