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

import com.fu.fuatsbe.DTO.PlanDetailActionDTO;
import com.fu.fuatsbe.DTO.PlanDetailCreateDTO;
import com.fu.fuatsbe.constant.planDetail.PlanDetailSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.ListResponseDTO;
import com.fu.fuatsbe.response.PlanDetailResponseDTO;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.service.PlanDetailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/planDetail")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PlanDetailController {

    private final PlanDetailService planDetailService;

    @GetMapping("/getAll")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllPlanDetails() {
        ListResponseDTO<PlanDetailResponseDTO> responseDTO = new ListResponseDTO();
        List<PlanDetailResponseDTO> list = planDetailService.getAllPlanDetails();
        responseDTO.setData(list);
        responseDTO.setMessage(PlanDetailSuccessMessage.GET_ALL_PLAN_DETAIL_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getByRecruitmentPlan")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getByRecuitmentPlan(@RequestParam("id") int recruitmentId) {
        ListResponseDTO<PlanDetailResponseDTO> responseDTO = new ListResponseDTO();
        List<PlanDetailResponseDTO> list = planDetailService.getAllByRecruitmentPlans(recruitmentId);
        responseDTO.setData(list);
        responseDTO.setMessage(PlanDetailSuccessMessage.GET_PLAN_DETAIL_BY_RECRUITMENT_PLAN_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getPlanDetailById(@RequestParam(name = "id") int id) {
        ResponseDTO<PlanDetailResponseDTO> responseDTO = new ResponseDTO();
        PlanDetailResponseDTO planDetail = planDetailService.getPlanDetailById(id);
        responseDTO.setData(planDetail);
        responseDTO.setMessage(PlanDetailSuccessMessage.GET_PLAN_DETAIL_BY_ID_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/create")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> createPlanDetail(@RequestBody PlanDetailCreateDTO createDTO) {
        ResponseDTO<PlanDetailResponseDTO> responseDTO = new ResponseDTO();
        PlanDetailResponseDTO planDetailDTO = planDetailService.createPlanDetail(createDTO);
        responseDTO.setData(planDetailDTO);
        responseDTO.setMessage("create plan detail success");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getPendingPlanDetails")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getPendingPlanDetails() {
        ListResponseDTO<PlanDetailResponseDTO> responseDTO = new ListResponseDTO();
        List<PlanDetailResponseDTO> list = planDetailService.getPendingPlanDetails();
        responseDTO.setData(list);
        responseDTO.setMessage(PlanDetailSuccessMessage.GET_ALL_PLAN_DETAIL_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getApprovedPlanDetails")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getApprovedPlanDetails() {
        ListResponseDTO<PlanDetailResponseDTO> responseDTO = new ListResponseDTO();
        List<PlanDetailResponseDTO> list = planDetailService.getApprovedPlanDetails();
        responseDTO.setData(list);
        responseDTO.setMessage(PlanDetailSuccessMessage.GET_ALL_PLAN_DETAIL_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getCanceledPlanDetails")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getCanceledPlanDetails() {
        ListResponseDTO<PlanDetailResponseDTO> responseDTO = new ListResponseDTO();
        List<PlanDetailResponseDTO> list = planDetailService.getCanceledPlanDetails();
        responseDTO.setData(list);
        responseDTO.setMessage(PlanDetailSuccessMessage.GET_ALL_PLAN_DETAIL_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/approved")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> approvedPlanDetails(@RequestBody PlanDetailActionDTO actionDTO) {
        ResponseDTO<PlanDetailResponseDTO> responseDTO = new ResponseDTO();
        PlanDetailResponseDTO planDetailDTO = planDetailService.approvedPlanDetails(actionDTO);
        responseDTO.setData(planDetailDTO);
        responseDTO.setMessage("approved plan detail success");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/canceled")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> canceledPlanDetails(@RequestBody PlanDetailActionDTO actionDTO) {
        ResponseDTO<PlanDetailResponseDTO> responseDTO = new ResponseDTO();
        PlanDetailResponseDTO planDetailDTO = planDetailService.canceledPlanDetails(actionDTO);
        responseDTO.setData(planDetailDTO);
        responseDTO.setMessage("canceled plan detail success");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

}
