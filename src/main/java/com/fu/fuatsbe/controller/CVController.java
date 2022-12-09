package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.constant.cv.CVErrorMessage;

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

import com.fu.fuatsbe.DTO.CvCreateDTO;
import com.fu.fuatsbe.DTO.CvUpdateDTO;
import com.fu.fuatsbe.constant.cv.CVSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.entity.CV;
import com.fu.fuatsbe.response.CvResponse;
import com.fu.fuatsbe.response.ListResponseDTO;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.response.TitleAndValueResponse;
import com.fu.fuatsbe.service.CVService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cv")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CVController {

    private final CVService cvService;

    @GetMapping("/getAllCvs")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllCvs(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<CvResponse> list = cvService.getAllCvs(pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(CVSuccessMessage.GET_ALL_CVS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllCvByCandidate")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> getAllCvByCandidate(
            @RequestParam("id") int id,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<CvResponse> list = cvService.getAllCvByCandidate(id, pageNo, pageSize);
        if (list.getResponseList() == null) {
            responseDTO.setMessage(CVErrorMessage.CANDIDATE_CV_EMPTY);
        } else {
            responseDTO.setData(list);
            responseDTO.setMessage(CVSuccessMessage.GET_ALL_CVS);
        }
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllCvRejected")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllCvRejected() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(cvService.getRejectedCv());
        responseDTO.setMessage(CVSuccessMessage.GET_CV_REJECTED_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/create")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> createCV(@RequestBody CvCreateDTO createDTO) {
        ResponseDTO<CvResponse> responseDTO = new ResponseDTO();
        CvResponse cvResponse = cvService.createCV(createDTO);
        responseDTO.setData(cvResponse);
        responseDTO.setMessage(CVSuccessMessage.CREATE_CV);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("edit/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_CANDIDATE)
    public ResponseEntity<ResponseDTO> updateDepartment(@RequestParam("id") int id,
            @RequestBody CvUpdateDTO updateDTO) {
        ResponseDTO<CvResponse> responseDTO = new ResponseDTO();
        CvResponse cv = cvService.updateCV(id, updateDTO);
        responseDTO.setData(cv);
        responseDTO.setMessage(CVSuccessMessage.UPDATE_CV);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity deleteCV(@RequestParam("id") int id) {
        ResponseDTO<CV> responseDTO = new ResponseDTO();
        cvService.deleteCV(id);
        responseDTO.setMessage(CVSuccessMessage.DELETE_CV);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getCvStorage")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getCvStorage() {
        ListResponseDTO<CvResponse> responseDTO = new ListResponseDTO();
        List<CvResponse> list = cvService.getCvStore();
        responseDTO.setData(list);
        responseDTO.setMessage(CVSuccessMessage.GET_ALL_CVS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getCVForRequest")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getCVForRequest(@RequestParam String positionName) {
        ListResponseDTO<CvResponse> responseDTO = new ListResponseDTO();
        List<CvResponse> list = cvService.getCvForRequest(positionName);
        responseDTO.setData(list);
        responseDTO.setMessage(CVSuccessMessage.GET_ALL_CVS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllSourceCV")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllSourceCV() {
        ListResponseDTO<TitleAndValueResponse> responseDTO = new ListResponseDTO();
        List<TitleAndValueResponse> list = cvService.getAllSourceCV();
        responseDTO.setData(list);
        responseDTO.setMessage(CVSuccessMessage.GET_ALL_CVS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

}
