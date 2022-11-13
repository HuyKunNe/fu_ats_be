package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.response.IdAndNameResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.fu.fuatsbe.DTO.RecruimentPlanUpdateDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanActionDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanCreateDTO;
import com.fu.fuatsbe.constant.recruitmentPlan.RecruitmentPlanSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.response.RecruitmentPlanResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.RecruitmentPlanService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/recruitmentPlan")
@CrossOrigin("*")
@RequiredArgsConstructor
public class RecruitmentPlanController {

    @Autowired
    private final RecruitmentPlanService recruitmentPlanService;

    @GetMapping("/getAllRecruitmentPlans")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllRecruitmentPlans(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        ResponseDTO<ResponseWithTotalPage> response = new ResponseDTO();
        ResponseWithTotalPage<RecruitmentPlanResponse> list = recruitmentPlanService.getAllRecruitmentPlans(pageNo,
                pageSize);
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_ALL_RECRUITMENT_PLAN_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getAllApprovedRecruitmentPlans")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllApprovedRecruitmentPlans(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        ResponseDTO<ResponseWithTotalPage> response = new ResponseDTO();
        ResponseWithTotalPage<RecruitmentPlanResponse> list = recruitmentPlanService
                .getAllApprovedRecruitmentPlan(pageNo, pageSize);
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_APPROVED_RECRUITMENT_PLAN_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getAllRejectedRecruitmentPlans")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllRejectedRecruitmentPlans(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> response = new ResponseDTO();
        ResponseWithTotalPage<RecruitmentPlanResponse> list = recruitmentPlanService
                .getAllRejectedRecruitmentPlans(pageNo, pageSize);
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_REJECTED_RECRUITMENT_PLAN_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getAllPendingRecruitmentPlans")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllPendingRecruitmentPlans(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        ResponseDTO<ResponseWithTotalPage> response = new ResponseDTO();
        ResponseWithTotalPage<RecruitmentPlanResponse> list = recruitmentPlanService
                .getAllPedingRecruitmentPlans(pageNo, pageSize);
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_PENDING_RECRUITMENT_PLAN_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getAllCanceledRecruitmentPlans")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllCanceledRecruitmentPlans(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        ResponseDTO<ResponseWithTotalPage> response = new ResponseDTO();
        ResponseWithTotalPage<RecruitmentPlanResponse> list = recruitmentPlanService
                .getAllPedingRecruitmentPlans(pageNo, pageSize);
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_CANCELED_RECRUITMENT_PLAN_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getRecruitmentPlanById(@RequestParam("id") int id) {
        ResponseDTO<RecruitmentPlanResponse> responseDTO = new ResponseDTO();
        RecruitmentPlanResponse recruitmentPlan = recruitmentPlanService.getRecruitmentPlanById(id);
        responseDTO.setData(recruitmentPlan);
        responseDTO.setMessage(RecruitmentPlanSuccessMessage.GET_RECRUITMENT_PLAN_BY_ID_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getByApprover/")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getByApproverId(
            @RequestParam("id") int id,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        ResponseDTO<ResponseWithTotalPage> response = new ResponseDTO();
        ResponseWithTotalPage<RecruitmentPlanResponse> list = recruitmentPlanService.getAllRecruitmentPlansByApprover(
                id, pageNo,
                pageSize);
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_RECRUITMENT_PLAN_BY_APPROVER_ID_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getByCreator")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getByCreatorId(
            @RequestParam("id") int id,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        ResponseDTO<ResponseWithTotalPage> response = new ResponseDTO();
        ResponseWithTotalPage<RecruitmentPlanResponse> list = recruitmentPlanService.getAllRecruitmentPlansByCreator(id,
                pageNo,
                pageSize);
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_RECRUITMENT_PLAN_BY_CREATOR_ID_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/getByDepartment")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getByDepartmentId(
            @RequestParam("departmentId") int id,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        ResponseDTO<ResponseWithTotalPage> response = new ResponseDTO();
        ResponseWithTotalPage<RecruitmentPlanResponse> list = recruitmentPlanService.getAllRecruitmentPlansByDepartment(id,
                pageNo,
                pageSize);
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_RECRUITMENT_PLAN_BY_DEPARTMENT_ID_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/getPlanApprovedByDepartment")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getPlanApprovedByDepartmentId(@RequestParam("departmentId") int id) {
        ResponseDTO response = new ResponseDTO();
        List<IdAndNameResponse> list = recruitmentPlanService.getApprovedByDepartment(id);
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_APPROVED_RECRUITMENT_PLAN_BY_DEPARTMENT_ID_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> createRecruitmentPlan(
            @RequestBody RecruitmentPlanCreateDTO createDTO) {
        ResponseDTO<RecruitmentPlanResponse> responseDTO = new ResponseDTO();
        RecruitmentPlanResponse recruitmentPlanResponse = recruitmentPlanService.createRecruitmentPlan(createDTO);
        responseDTO.setData(recruitmentPlanResponse);
        responseDTO.setMessage(RecruitmentPlanSuccessMessage.CREATE_RECRUITMENT_PLAN_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/approved")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> approvedRecruitmentPlan(
            @RequestBody RecruitmentPlanActionDTO actionDTO) {
        ResponseDTO<RecruitmentPlanResponse> responseDTO = new ResponseDTO();
        RecruitmentPlanResponse recruitmentPlanResponse = recruitmentPlanService.approvedRecruitmentPlan(actionDTO);
        responseDTO.setData(recruitmentPlanResponse);
        responseDTO.setMessage(RecruitmentPlanSuccessMessage.APPROVED_RECRUITMENT_PLAN_SUCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/rejected")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> rejectedRecruitmentPlan(
            @RequestBody RecruitmentPlanActionDTO actionDTO) {
        ResponseDTO<RecruitmentPlanResponse> responseDTO = new ResponseDTO();
        RecruitmentPlanResponse recruitmentPlanResponse = recruitmentPlanService.rejectedRecruitmentPlan(actionDTO);
        responseDTO.setData(recruitmentPlanResponse);
        responseDTO.setMessage(RecruitmentPlanSuccessMessage.REJECTED_RECRUITMENT_PLAN_SUCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/canceled")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> canceledRecruitmentPlan(
            @RequestBody RecruitmentPlanActionDTO actionDTO) {
        ResponseDTO<RecruitmentPlanResponse> responseDTO = new ResponseDTO();
        RecruitmentPlanResponse recruitmentPlanResponse = recruitmentPlanService.canceledRecruitmentPlan(actionDTO);
        responseDTO.setData(recruitmentPlanResponse);
        responseDTO.setMessage(RecruitmentPlanSuccessMessage.CANCELED_RECRUITMENT_PLAN_SUCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("update/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> updateRecruitmentPlan(@RequestParam("id") int id,
            @RequestBody RecruimentPlanUpdateDTO updateDTO) {
        ResponseDTO<RecruitmentPlanResponse> responseDTO = new ResponseDTO();
        RecruitmentPlanResponse recruitmentPlanResponse = recruitmentPlanService.updateRecruitmentPlan(id, updateDTO);
        responseDTO.setData(recruitmentPlanResponse);
        responseDTO.setMessage(RecruitmentPlanSuccessMessage.UPDATE_RECRUITMENT_PLAN_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
