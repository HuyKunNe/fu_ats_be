package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.CandidateUpdateDTO;
import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.response.CandidateResponseDTO;

public interface CandidateService {
    public List<CandidateResponseDTO> getAllCandidates();

    public CandidateResponseDTO getCandidateById(int id);

    public CandidateResponseDTO getCandidateByEmail(String email);

    public CandidateResponseDTO getCandidateByPhone(String phone);

    public CandidateResponseDTO updateCandidateById(CandidateUpdateDTO updateDTO);

    public Candidate deleteCandidateById(int id);
}
