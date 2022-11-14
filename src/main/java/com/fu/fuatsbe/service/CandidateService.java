package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.CandidateUpdateDTO;
import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.response.CandidateResponseDTO;
import com.fu.fuatsbe.response.IdAndNameResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

import java.util.List;

public interface CandidateService {
    public ResponseWithTotalPage<CandidateResponseDTO> getAllCandidates(int pageNo, int pageSize);

    public ResponseWithTotalPage<CandidateResponseDTO> getActivateCandidates(int pageNo, int pageSize);

    public ResponseWithTotalPage<CandidateResponseDTO> getDisableCandidates(int pageNo, int pageSize);

    public CandidateResponseDTO getCandidateById(int id);

    public CandidateResponseDTO getCandidateByEmail(String email);

    public CandidateResponseDTO getCandidateByPhone(String phone);

    public CandidateResponseDTO updateCandidateById(int id, CandidateUpdateDTO updateDTO);

    public Candidate deleteCandidateById(int id);
    List<IdAndNameResponse> getCandidateAppliedByRecruitment(int recruitment);
}
