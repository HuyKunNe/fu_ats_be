package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.RecruitmentRequestCreateDTO;
import com.fu.fuatsbe.DTO.RecruitmentRequestUpdateDTO;
import com.fu.fuatsbe.response.RecruitmentRequestResponse;

public interface RecruitmentRequestService {
    public List<RecruitmentRequestResponse> getAllRecruitmentRequests();

    public RecruitmentRequestResponse getRecruitmentRequestById(int id);

    public List<RecruitmentRequestResponse> getAllOpenRecruitmentRequest();

    public List<RecruitmentRequestResponse> getAllFilledRecruitmentRequest();

    public List<RecruitmentRequestResponse> getAllClosedRecruitmentRequest();

    public List<RecruitmentRequestResponse> getAllRecruitmentRequestByCreator(int id);

    public RecruitmentRequestResponse updateRecruitmentRequest(int id, RecruitmentRequestUpdateDTO updateDTO);

    public RecruitmentRequestResponse createRecruitmentRequest(RecruitmentRequestCreateDTO createDTO);

    public RecruitmentRequestResponse closeRecruitmentRequest(int id);

}
