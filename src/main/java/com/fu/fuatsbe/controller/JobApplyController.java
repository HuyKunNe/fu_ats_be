package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.response.IdAndNameResponse;
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
import com.fu.fuatsbe.entity.CVScreening;
import com.fu.fuatsbe.response.JobApplyResponse;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.JobApplyService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/jobApply")
@CrossOrigin("*")
@RequiredArgsConstructor
public class JobApplyController {

    private final JobApplyService jobApplyService;

    @PostMapping("/create")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> createInterview(@RequestBody JobApplyCreateDTO createDTO) {
        ResponseDTO<JobApplyResponse> responseDTO = new ResponseDTO();
        JobApplyResponse jobApplyResponse = jobApplyService.createJobApply(createDTO);
        responseDTO.setData(jobApplyResponse);
        responseDTO.setMessage(JobApplySuccessMessage.CREATE_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAll")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllJobApplies(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<JobApplyResponse> list = jobApplyService.getAllJobApplies(pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(JobApplySuccessMessage.GET_ALL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getByRecruitmentRequest")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllJobAppliesByRecruitmentRequest(
            @RequestParam int requestId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<JobApplyResponse> list = jobApplyService.getAllJobAppliesByRecruitmentRequest(requestId,
                pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(JobApplySuccessMessage.GET_ALL_BY_RECRUITMENT_REQUEST);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getJobApplyByCandidate")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> getJobApplyByCandidate(
            @RequestParam int candidateId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<JobApplyResponse> list = jobApplyService.getJobApplyByCandidate(candidateId, pageNo,
                pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(JobApplySuccessMessage.GET_ALL_BY_CANDIDATE);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllPendingJobApplies")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> getAllPendingJobApplies(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<JobApplyResponse> list = jobApplyService.getAllPendingJobApplies(pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(JobApplySuccessMessage.GET_ALL_PENDING);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllApprovedJobApplies")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllApprovedJobApplies(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<JobApplyResponse> list = jobApplyService.getAllApprovedJobApplies(pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(JobApplySuccessMessage.GET_ALL_APPROVED);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllCancelJobApplies")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllCancelJobApplies(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<JobApplyResponse> list = jobApplyService.getAllCancelJobApplies(pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(JobApplySuccessMessage.GET_ALL_CANCELED);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/cancelJobApply/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> cancelJobApply(@RequestParam("id") int id,
            @RequestParam int employeeId) {
        ResponseDTO<JobApplyResponse> responseDTO = new ResponseDTO();
        JobApplyResponse jobApplyResponse = jobApplyService.cancelJobApply(id, employeeId);
        responseDTO.setData(jobApplyResponse);
        responseDTO.setMessage(JobApplySuccessMessage.CANCEL);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/approvedJobApply/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> approvedJobApply(@RequestParam("id") int id,
            @RequestParam int employeeId) {
        ResponseDTO<JobApplyResponse> responseDTO = new ResponseDTO();
        JobApplyResponse jobApplyResponse = jobApplyService.approvedJobApply(id, employeeId);
        responseDTO.setData(jobApplyResponse);
        responseDTO.setMessage(JobApplySuccessMessage.APPROVE);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("getById/{id}")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> getJobApplyById(@RequestParam("id") int id) {
        ResponseDTO<JobApplyResponse> responseDTO = new ResponseDTO();
        JobApplyResponse jobApplyResponse = jobApplyService.getJobApplyById(id);
        responseDTO.setData(jobApplyResponse);
        responseDTO.setMessage(JobApplySuccessMessage.GET_BY_ID);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getJobApplyDepartment")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getJobApplyByDepartment(@RequestParam int departmentId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO responseDTO = new ResponseDTO();
        ResponseWithTotalPage<JobApplyResponse> responseList = jobApplyService.getJobApplyByDepartment(departmentId,
                pageNo, pageSize);
        responseDTO.setData(responseList);
        responseDTO.setMessage(JobApplySuccessMessage.GET_BY_ID);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getJobApplyNotReject")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getJobApplyNotReject(@RequestParam int recruitmentRequestId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO responseDTO = new ResponseDTO();
        ResponseWithTotalPage<JobApplyResponse> responseList = jobApplyService.getJobApplyNotReject(
                recruitmentRequestId,
                pageNo, pageSize);
        responseDTO.setData(responseList);
        responseDTO.setMessage(JobApplySuccessMessage.GET_JOB_APPLY_NOT_REJECT);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getCVScreening")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getCVScreening() {
        ResponseDTO responseDTO = new ResponseDTO();
        CVScreening cvScreening = jobApplyService.getCVScreening();
        responseDTO.setData(cvScreening);
        responseDTO.setMessage(JobApplySuccessMessage.GET_JOB_APPLY_NOT_REJECT);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getJobApplyPassScreening")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getJobApplyPassScreening(@RequestParam int recruitmentRequestId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO responseDTO = new ResponseDTO();
        ResponseWithTotalPage<JobApplyResponse> responseList = jobApplyService.getJobApplyPassScreening(
                recruitmentRequestId, pageNo, pageSize);
        responseDTO.setData(responseList);
        responseDTO.setMessage(JobApplySuccessMessage.GET_JOB_APPLY_NOT_REJECT);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/checkApplyByCandidateAndRequest")
    @PreAuthorize(RolePreAuthorize.ROLE_CANDIDATE)
    public boolean checkApplyByCandidateAndRequest(@RequestParam int requestId, @RequestParam int candidateId) {
        return jobApplyService.checkApplyByRecruitmentRequestAndCandidate(requestId, candidateId);
    }

}
