package com.fu.fuatsbe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fu.fuatsbe.DTO.ReportDTO;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.ListResponseDTO;
import com.fu.fuatsbe.response.ReportGroupByDepartment;
import com.fu.fuatsbe.service.JobApplyService;
import com.fu.fuatsbe.service.RecruitmentPlanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/report")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ReportController {

    private final JobApplyService jobApplyService;
    private final RecruitmentPlanService recruitmentPlanService;

    @PutMapping("/getReport")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> report(@RequestBody ReportDTO reportDTO) {
        ListResponseDTO<ReportGroupByDepartment> responseDTO = new ListResponseDTO();
        List<ReportGroupByDepartment> list = jobApplyService.getReport(reportDTO.getYear(),
                reportDTO.getDepartmentName());
        responseDTO.setData(list);
        responseDTO.setMessage("Get report successfully");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getYearFromPlan")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getYearFromPlan() {
        ListResponseDTO<String> responseDTO = new ListResponseDTO();
        List<String> list = recruitmentPlanService.getYearFromPlan();
        responseDTO.setData(list);
        responseDTO.setMessage("Get year from plan successfully");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
