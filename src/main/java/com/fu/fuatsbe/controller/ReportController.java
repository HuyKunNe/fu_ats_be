package com.fu.fuatsbe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.ListResponseDTO;
import com.fu.fuatsbe.response.ReportGroupByJobRequest;
import com.fu.fuatsbe.service.JobApplyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/report")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ReportController {

    private final JobApplyService jobApplyService;

    @GetMapping("/getReport")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> report() {
        ListResponseDTO<ReportGroupByJobRequest> responseDTO = new ListResponseDTO();
        List<ReportGroupByJobRequest> list = jobApplyService.getReport();
        responseDTO.setData(list);
        responseDTO.setMessage("Get report successfully");
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
