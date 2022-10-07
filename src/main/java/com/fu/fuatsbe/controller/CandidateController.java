package com.fu.fuatsbe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fu.fuatsbe.constant.candidate.CandidateErrorMessage;
import com.fu.fuatsbe.constant.candidate.CandidateSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.response.CandidateResponseDTO;
import com.fu.fuatsbe.response.ListResponseDTO;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.service.CandidateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/candidate")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/getAllCandidates")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllCandidates() {
        ListResponseDTO<CandidateResponseDTO> responseDTO = new ListResponseDTO();
        List<CandidateResponseDTO> list = candidateService.getAllCandidates();
        responseDTO.setData(list);
        responseDTO.setMessage(CandidateSuccessMessage.GET_ALL_CANDIDATE_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllActivateCandidates")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllActivateCandidates() {
        ListResponseDTO<CandidateResponseDTO> responseDTO = new ListResponseDTO();
        List<CandidateResponseDTO> list = candidateService.getActivateCandidates();
        responseDTO.setData(list);
        responseDTO.setMessage(CandidateSuccessMessage.GET_ALL_CANDIDATE_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllDisableCandidates")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ListResponseDTO> getAllDisableCandidates() {
        ListResponseDTO<CandidateResponseDTO> responseDTO = new ListResponseDTO();
        List<CandidateResponseDTO> list = candidateService.getDisableCandidates();
        responseDTO.setData(list);
        responseDTO.setMessage(CandidateSuccessMessage.GET_ALL_CANDIDATE_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getCandidateById/{id}")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> getCandidateById(@RequestParam("id") int id) {
        ResponseDTO<CandidateResponseDTO> responseDTO = new ResponseDTO();
        CandidateResponseDTO candidate = candidateService.getCandidateById(id);
        responseDTO.setData(candidate);
        responseDTO.setMessage(CandidateSuccessMessage.GET_CANDIDATE_BY_ID_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getCandidateByPhone/{phone}")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> getCandidateByPhone(@RequestParam("phone") String phone) {
        ResponseDTO<CandidateResponseDTO> responseDTO = new ResponseDTO();
        CandidateResponseDTO candidate = candidateService.getCandidateByPhone(phone);
        responseDTO.setData(candidate);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setMessage(CandidateSuccessMessage.GET_CANDIDATE_BY_PHONE_SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getCandidateByEmail/{email}")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> getCandidateByEmail(@RequestParam("email") String email) {
        ResponseDTO<CandidateResponseDTO> responseDTO = new ResponseDTO();
        CandidateResponseDTO candidate = candidateService.getCandidateByEmail(email);
        responseDTO.setData(candidate);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setMessage(CandidateSuccessMessage.GET_CANDIDATE_BY_EMAIL_SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> deleteCandidateById(@RequestParam(name = "id") int id) {
        ResponseDTO<Boolean> responseDTO = new ResponseDTO();
        Candidate candidate = candidateService.deleteCandidateById(id);
        responseDTO.setData(true);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        responseDTO.setMessage(CandidateSuccessMessage.DELETE_CANDIDATE_SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

}
