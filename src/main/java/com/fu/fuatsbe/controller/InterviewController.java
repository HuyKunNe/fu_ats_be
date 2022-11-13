package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.DTO.InterviewCreateDTO;
import com.fu.fuatsbe.DTO.InterviewUpdateDTO;
import com.fu.fuatsbe.constant.interview.InterviewSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.InterviewResponse;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/interview")
@CrossOrigin("*")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @GetMapping("/getAllInterview")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getAllInterview(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO response = new ResponseDTO();
        response.setData(interviewService.getAllInterview(pageNo, pageSize));
        response.setStatus(ResponseStatusDTO.SUCCESS);
        response.setMessage(InterviewSuccessMessage.GET_ALL_INTERVIEWS);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/createInterview")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> createInterview(@RequestBody InterviewCreateDTO dto) throws MessagingException {
        ResponseDTO response = new ResponseDTO();
        InterviewResponse interviewResponse = interviewService.createInterview(dto);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        response.setData(interviewResponse);
        response.setMessage(InterviewSuccessMessage.CREATE_INTERVIEW_SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getInterviewByCandidateID")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> getInterviewByCandidateID(@RequestParam int candidateId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(interviewService.getInterviewByCandidateID(candidateId, pageNo, pageSize));
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setMessage(InterviewSuccessMessage.GET_INTERVIEW_BY_CANDIDATE_ID);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getInterviewByEmployeeID")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> getInterviewByEmployeeID(@RequestParam int employeeId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO responseDTO = new ResponseDTO();
        ResponseWithTotalPage<InterviewResponse> interviewResponses = interviewService
                .getInterviewByEmployeeID(employeeId, pageNo, pageSize);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setData(interviewResponses);
        responseDTO.setMessage(InterviewSuccessMessage.GET_INTERVIEW_BY_EMPLOYEE_ID);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getInterviewByDepartment")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> getInterviewByDepartment(@RequestParam int departmentId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO responseDTO = new ResponseDTO();
        ResponseWithTotalPage<InterviewResponse> interviewResponses = interviewService
                .getInterviewByDepartment(departmentId, pageNo, pageSize);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setData(interviewResponses);
        responseDTO.setMessage(InterviewSuccessMessage.GET_INTERVIEW_BY_EMPLOYEE_ID);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/updateInterview")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> updateInterview(@RequestParam int interviewId,
            @RequestBody InterviewUpdateDTO interviewUpdateDTO) throws MessagingException {
        ResponseDTO response = new ResponseDTO();
        response.setData(interviewService.updateInterview(interviewId, interviewUpdateDTO));
        response.setStatus(ResponseStatusDTO.SUCCESS);
        response.setMessage(InterviewSuccessMessage.UPDATE_INTERVIEW);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/closeInterview")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> closeInterview(@RequestParam int id) {
        ResponseDTO response = new ResponseDTO();
        interviewService.closeInterview(id);
        response.setMessage(InterviewSuccessMessage.CLOSE_INTERVIEW);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/cancelInterview")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> cancelInterview(@RequestParam int id) {
        ResponseDTO response = new ResponseDTO();
        interviewService.cancelInterview(id);
        response.setMessage(InterviewSuccessMessage.CANCEL_INTERVIEW);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getInterviewById")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> getInterviewById(@RequestParam int id) {
        ResponseDTO response = new ResponseDTO();
        response.setData(interviewService.getInterviewByID(id));
        response.setStatus(ResponseStatusDTO.SUCCESS);
        response.setMessage(InterviewSuccessMessage.GET_INTERVIEW_BY_ID);
        return ResponseEntity.ok().body(response);
    }
}
