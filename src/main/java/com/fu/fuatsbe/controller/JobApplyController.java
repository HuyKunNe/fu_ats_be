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

import com.fu.fuatsbe.DTO.JobApplyCreateDTO;
import com.fu.fuatsbe.constant.job_apply.JobApplySuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.JobApplyResponse;
import com.fu.fuatsbe.response.ListResponseDTO;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.service.JobApplyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/jobApply")
@CrossOrigin("*")
@RequiredArgsConstructor
public class JobApplyController {

    private final JobApplyService jobApplyService;

    @PostMapping("/create")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE_CANDIDATE)
    public ResponseEntity<ResponseDTO> createInterview(@RequestBody JobApplyCreateDTO createDTO) {
        ResponseDTO<JobApplyResponse> responseDTO = new ResponseDTO();
        JobApplyResponse jobApplyResponse = jobApplyService.createJobApply(createDTO);
        responseDTO.setData(jobApplyResponse);
        responseDTO.setMessage(JobApplySuccessMessage.CREATE_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAll")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllJobApplies(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ListResponseDTO<JobApplyResponse> responseDTO = new ListResponseDTO();
        List<JobApplyResponse> list = jobApplyService.getAllJobApplies(pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(JobApplySuccessMessage.GET_ALL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getByRecruitmentRequest")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllJobAppliesByRecruitmentRequest(@RequestParam int requestId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ListResponseDTO<JobApplyResponse> responseDTO = new ListResponseDTO();
        List<JobApplyResponse> list = jobApplyService.getAllJobAppliesByRecruitmentRequest(requestId, pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(JobApplySuccessMessage.GET_ALL_BY_RECRUITMENT_REQUEST);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getJobApplyByCandidate")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE_CANDIDATE)
    public ResponseEntity<ListResponseDTO> getJobApplyByCandidate(@RequestParam int candidateId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ListResponseDTO<JobApplyResponse> responseDTO = new ListResponseDTO();
        List<JobApplyResponse> list = jobApplyService.getJobApplyByCandidate(candidateId, pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(JobApplySuccessMessage.GET_ALL_BY_CANDIDATE);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllPendingJobApplies")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getJobApplyByCandidate(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ListResponseDTO<JobApplyResponse> responseDTO = new ListResponseDTO();
        List<JobApplyResponse> list = jobApplyService.getAllPendingJobApplies(pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(JobApplySuccessMessage.GET_ALL_PENDING);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllApprovedJobApplies")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllApprovedJobApplies(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ListResponseDTO<JobApplyResponse> responseDTO = new ListResponseDTO();
        List<JobApplyResponse> list = jobApplyService.getAllApprovedJobApplies(pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(JobApplySuccessMessage.GET_ALL_APPROVED);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllCancelJobApplies")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllCancelJobApplies(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ListResponseDTO<JobApplyResponse> responseDTO = new ListResponseDTO();
        List<JobApplyResponse> list = jobApplyService.getAllCancelJobApplies(pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(JobApplySuccessMessage.GET_ALL_CANCELED);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/cancelJobApply/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> cancelJobApply(@RequestParam("id") int id, @RequestParam int employeeId) {
        ResponseDTO<JobApplyResponse> responseDTO = new ResponseDTO();
        JobApplyResponse jobApplyResponse = jobApplyService.cancelJobApply(id, employeeId);
        responseDTO.setData(jobApplyResponse);
        responseDTO.setMessage(JobApplySuccessMessage.CANCEL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/approvedJobApply/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> approvedJobApply(@RequestParam("id") int id, @RequestParam int employeeId) {
        ResponseDTO<JobApplyResponse> responseDTO = new ResponseDTO();
        JobApplyResponse jobApplyResponse = jobApplyService.approvedJobApply(id, employeeId);
        responseDTO.setData(jobApplyResponse);
        responseDTO.setMessage(JobApplySuccessMessage.APPROVE);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("getById/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE_CANDIDATE)
    public ResponseEntity<ResponseDTO> getJobApplyById(@RequestParam("id") int id) {
        ResponseDTO<JobApplyResponse> responseDTO = new ResponseDTO();
        JobApplyResponse jobApplyResponse = jobApplyService.getJobApplyById(id);
        responseDTO.setData(jobApplyResponse);
        responseDTO.setMessage(JobApplySuccessMessage.GET_BY_ID);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

}
