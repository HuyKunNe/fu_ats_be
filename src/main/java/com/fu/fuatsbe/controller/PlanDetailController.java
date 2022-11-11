package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.DTO.PlanDetailUpdateDTO;
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
import com.fu.fuatsbe.response.PlanDetailResponseDTO;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
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
    public ResponseEntity<ResponseDTO> getAllPlanDetails(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<PlanDetailResponseDTO> list = planDetailService.getAllPlanDetails(pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(PlanDetailSuccessMessage.GET_ALL_PLAN_DETAIL_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getByRecruitmentPlan")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getByRecuitmentPlan(
            @RequestParam("id") int recruitmentId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<PlanDetailResponseDTO> list = planDetailService.getAllByRecruitmentPlans(recruitmentId,
                pageNo, pageSize);
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
    public ResponseEntity<ResponseDTO> createPlanDetail(
            @RequestBody PlanDetailCreateDTO createDTO) {
        ResponseDTO<PlanDetailResponseDTO> responseDTO = new ResponseDTO();
        PlanDetailResponseDTO planDetailDTO = planDetailService.createPlanDetail(createDTO);
        responseDTO.setData(planDetailDTO);
        responseDTO.setMessage(PlanDetailSuccessMessage.CREATE_PLAN_DETAIL_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getPendingPlanDetails")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getPendingPlanDetails(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<PlanDetailResponseDTO> list = planDetailService.getPendingPlanDetails(pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(PlanDetailSuccessMessage.GET_ALL_PLAN_DETAIL_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getApprovedPlanDetails")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getApprovedPlanDetails(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<PlanDetailResponseDTO> list = planDetailService.getApprovedPlanDetails(pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(PlanDetailSuccessMessage.GET_ALL_PLAN_DETAIL_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getCanceledPlanDetails")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getCanceledPlanDetails(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<PlanDetailResponseDTO> list = planDetailService.getCanceledPlanDetails(pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(PlanDetailSuccessMessage.GET_ALL_PLAN_DETAIL_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/update")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> updatePlanDetails(@RequestParam int id, @RequestBody PlanDetailUpdateDTO updateDTO) {
        ResponseDTO<PlanDetailResponseDTO> responseDTO = new ResponseDTO();
        responseDTO.setData(planDetailService.updatePlanDetailById(id, updateDTO));
        responseDTO.setMessage(PlanDetailSuccessMessage.UPDATE_PLAN_DETAIL_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/approved")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> approvedPlanDetails(
            @RequestBody PlanDetailActionDTO actionDTO) {
        ResponseDTO<PlanDetailResponseDTO> responseDTO = new ResponseDTO();
        PlanDetailResponseDTO planDetailDTO = planDetailService.approvedPlanDetails(actionDTO);
        responseDTO.setData(planDetailDTO);
        responseDTO.setMessage(PlanDetailSuccessMessage.APPROVE_PLAN_DETAIL_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/canceled")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> canceledPlanDetails(
            @RequestBody PlanDetailActionDTO actionDTO) {
        ResponseDTO<PlanDetailResponseDTO> responseDTO = new ResponseDTO();
        PlanDetailResponseDTO planDetailDTO = planDetailService.canceledPlanDetails(actionDTO);
        responseDTO.setData(planDetailDTO);
        responseDTO.setMessage(PlanDetailSuccessMessage.CANCEL_PLAN_DETAIL_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getPlanDetailByApprover")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getPlanDetailByApprover(
            @RequestParam("id") int approverId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<PlanDetailResponseDTO> list = planDetailService.getPlanDetailByApprover(approverId,
                pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(PlanDetailSuccessMessage.GET_ALL_PLAN_DETAIL_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

}
