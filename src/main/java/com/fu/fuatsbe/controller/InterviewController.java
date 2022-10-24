package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.DTO.InterviewCreateDTO;
import com.fu.fuatsbe.constant.interview.InterviewSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.InterviewCreateResponse;
import com.fu.fuatsbe.response.ResponseDTO;
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

    @GetMapping("/getInterviewNoNotification")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getInterviewNoNotification() {
        ResponseDTO response = new ResponseDTO();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/createInterview")
    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
    public ResponseEntity<ResponseDTO> createInterview(@RequestBody InterviewCreateDTO dto) throws MessagingException {
        ResponseDTO response = new ResponseDTO();
        InterviewCreateResponse interviewResponse = interviewService.createInterview(dto);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        response.setData(interviewResponse);
        response.setMessage(InterviewSuccessMessage.CREATE_INTERVIEW_SUCCESS);
        return ResponseEntity.ok().body(response);
    }
}
