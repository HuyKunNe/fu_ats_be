package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.DTO.InterviewDetailDTO;
import com.fu.fuatsbe.constant.interview_detail.InterviewDetailSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.ListResponseDTO;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.service.InterviewDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview-detail")
@CrossOrigin("*")
@RequiredArgsConstructor
public class InterviewDetailController {
    private final InterviewDetailService interviewDetailService;

    @GetMapping("/getAllInterviewDetail")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllInterviewDetail(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO response = new ResponseDTO();
        response.setData(interviewDetailService.getAllInterviewDetail(pageNo, pageSize));
        response.setStatus(ResponseStatusDTO.SUCCESS);
        response.setMessage(InterviewDetailSuccessMessage.GET_ALL_INTERVIEW_DETAIL_SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getInterviewDetailById")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> getInterviewDetailById(@RequestParam int id) {
        ResponseDTO response = new ResponseDTO();
        response.setData(interviewDetailService.getInterviewDetailById(id));
        response.setMessage(InterviewDetailSuccessMessage.GET_INTERVIEW_DETAIL_BY_ID_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/updateInterviewDetail")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> updateInterviewDetail(@RequestParam int id,
            @RequestBody InterviewDetailDTO interviewDetailDTO) {
        ResponseDTO response = new ResponseDTO();
        response.setData(interviewDetailService.updateInterviewDetail(id, interviewDetailDTO));
        response.setMessage(InterviewDetailSuccessMessage.UPDATE_INTERVIEW_DETAIL_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/createInterviewDetail")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> createInterviewDetail(@RequestBody InterviewDetailDTO interviewDetailDTO) {
        ResponseDTO response = new ResponseDTO();
        response.setData(interviewDetailService.createInterviewDetail(interviewDetailDTO));
        response.setMessage(InterviewDetailSuccessMessage.CREATE_INTERVIEW_DETAIL_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getByInterviewId")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> getInterviewDetailByInterviewId(@RequestParam int interviewId) {
        ResponseDTO response = new ResponseDTO();
        response.setData(interviewDetailService.getInterviewDetailByInterviewId(interviewId));
        response.setMessage(InterviewDetailSuccessMessage.GET_INTERVIEW_DETAIL_BY_INTERVIEW_ID_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getAllInterviewDetailByDepartment")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllInterviewDetailByDepartment(@RequestParam String departmentName,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO response = new ResponseDTO();
        response.setData(interviewDetailService.getAllInterviewDetailByDepartment(departmentName, pageNo, pageSize));
        response.setStatus(ResponseStatusDTO.SUCCESS);
        response.setMessage(InterviewDetailSuccessMessage.GET_ALL_INTERVIEW_DETAIL_SUCCESS);
        return ResponseEntity.ok().body(response);
    }
}
