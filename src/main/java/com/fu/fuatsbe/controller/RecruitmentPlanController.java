package com.fu.fuatsbe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fu.fuatsbe.DTO.RecruitmentPlanActionDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanCreateDTO;
import com.fu.fuatsbe.constant.recruitmentPlan.RecruitmentPlanErrorMessage;
import com.fu.fuatsbe.constant.recruitmentPlan.RecruitmentPlanSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.ListResponseDTO;
import com.fu.fuatsbe.response.RecruitmentPlanResponse;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.service.RecruitmentPlanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/recruitmentPlan")
@CrossOrigin("*")
@RequiredArgsConstructor
public class RecruitmentPlanController {

    @Autowired
    private final RecruitmentPlanService recruitmentPlanService;

    @GetMapping("/getAllRecruitmentPlans")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllRecruitmentPlans() {

        ListResponseDTO<RecruitmentPlanResponse> response = new ListResponseDTO();
        List<RecruitmentPlanResponse> list = recruitmentPlanService.getAllRecruitmentPlans();
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_ALL_RECRUITMENT_PLAN_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getAllApprovedRecruitmentPlans")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllApprovedRecruitmentPlans() {

        ListResponseDTO<RecruitmentPlanResponse> response = new ListResponseDTO();
        List<RecruitmentPlanResponse> list = recruitmentPlanService.getAllApprovedRecruitmentPlan();
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_APPROVED_RECRUITMENT_PLAN_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getAllRejectedRecruitmentPlans")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllRejectedRecruitmentPlans() {
        ListResponseDTO<RecruitmentPlanResponse> response = new ListResponseDTO();
        List<RecruitmentPlanResponse> list = recruitmentPlanService.getAllRejectedRecruitmentPlans();
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_REJECTED_RECRUITMENT_PLAN_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getAllPendingRecruitmentPlans")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllPendingRecruitmentPlans() {

        ListResponseDTO<RecruitmentPlanResponse> response = new ListResponseDTO();
        List<RecruitmentPlanResponse> list = recruitmentPlanService.getAllPedingRecruitmentPlans();
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_PENDING_RECRUITMENT_PLAN_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getAllCanceledRecruitmentPlans")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllCanceledRecruitmentPlans() {

        ListResponseDTO<RecruitmentPlanResponse> response = new ListResponseDTO();
        List<RecruitmentPlanResponse> list = recruitmentPlanService.getAllPedingRecruitmentPlans();
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_CANCELED_RECRUITMENT_PLAN_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getById")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getRecruitmentPlanById(@RequestParam("id") int id) {
        ResponseDTO<RecruitmentPlanResponse> responseDTO = new ResponseDTO();
        RecruitmentPlanResponse recruitmentPlan = recruitmentPlanService.getRecruitmentPlanById(id);
        responseDTO.setData(recruitmentPlan);
        responseDTO.setMessage(RecruitmentPlanSuccessMessage.GET_RECRUITMENT_PLAN_BY_ID_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getByApprover")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getByApproverId(@RequestParam("id") int id) {

        ListResponseDTO<RecruitmentPlanResponse> response = new ListResponseDTO();
        List<RecruitmentPlanResponse> list = recruitmentPlanService.getAllRecruitmentPlansByApprover(id);
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_RECRUITMENT_PLAN_BY_APPROVER_ID_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getByCreator")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getByCreatorId(@RequestParam("id") int id) {

        ListResponseDTO<RecruitmentPlanResponse> response = new ListResponseDTO();
        List<RecruitmentPlanResponse> list = recruitmentPlanService.getAllRecruitmentPlansByCreator(id);
        response.setData(list);
        response.setMessage(RecruitmentPlanSuccessMessage.GET_RECRUITMENT_PLAN_BY_CREATOR_ID_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> createRecruitmentPlan(@RequestBody RecruitmentPlanCreateDTO createDTO) {
        ResponseDTO<RecruitmentPlanResponse> responseDTO = new ResponseDTO();
        RecruitmentPlanResponse recruitmentPlanResponse = recruitmentPlanService.createRecruitmentPlan(createDTO);
        responseDTO.setData(recruitmentPlanResponse);
        responseDTO.setMessage("create recruitment plan success");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/approved")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> approvedRecruitmentPlan(@RequestBody RecruitmentPlanActionDTO actionDTO) {
        ResponseDTO<RecruitmentPlanResponse> responseDTO = new ResponseDTO();
        RecruitmentPlanResponse recruitmentPlanResponse = recruitmentPlanService.approvedRecruitmentPlan(actionDTO);
        responseDTO.setData(recruitmentPlanResponse);
        responseDTO.setMessage("approved recruitment plan success");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/rejected")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> rejectedRecruitmentPlan(@RequestBody RecruitmentPlanActionDTO actionDTO) {
        ResponseDTO<RecruitmentPlanResponse> responseDTO = new ResponseDTO();
        RecruitmentPlanResponse recruitmentPlanResponse = recruitmentPlanService.rejectedRecruitmentPlan(actionDTO);
        responseDTO.setData(recruitmentPlanResponse);
        responseDTO.setMessage("rejected recruitment plan success");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/canceled")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> canceledRecruitmentPlan(@RequestBody RecruitmentPlanActionDTO actionDTO) {
        ResponseDTO<RecruitmentPlanResponse> responseDTO = new ResponseDTO();
        RecruitmentPlanResponse recruitmentPlanResponse = recruitmentPlanService.canceledRecruitmentPlan(actionDTO);
        responseDTO.setData(recruitmentPlanResponse);
        responseDTO.setMessage("canceled recruitment plan success");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

}
