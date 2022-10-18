package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.RecruitmentRequestCreateDTO;
import com.fu.fuatsbe.DTO.RecruitmentRequestSearchDTO;
import com.fu.fuatsbe.DTO.RecruitmentRequestUpdateDTO;
import com.fu.fuatsbe.DTO.RecruitmentSearchCategoryDTO;
import com.fu.fuatsbe.response.RecruitmentRequestResponse;

public interface RecruitmentRequestService {
    public List<RecruitmentRequestResponse> getAllRecruitmentRequests(int pageNo, int pageSize);

    public RecruitmentRequestResponse getRecruitmentRequestById(int id);

    public List<RecruitmentRequestResponse> getAllOpenRecruitmentRequest(int pageNo, int pageSize);

    public List<RecruitmentRequestResponse> getAllFilledRecruitmentRequest(int pageNo, int pageSize);

    public List<RecruitmentRequestResponse> getAllClosedRecruitmentRequest(int pageNo, int pageSize);

    public List<RecruitmentRequestResponse> getAllRecruitmentRequestByCreator(int id, int pageNo, int pageSize);

    public RecruitmentRequestResponse updateRecruitmentRequest(int id, RecruitmentRequestUpdateDTO updateDTO);

    public RecruitmentRequestResponse createRecruitmentRequest(RecruitmentRequestCreateDTO createDTO);

    public RecruitmentRequestResponse closeRecruitmentRequest(int id);

    public List<RecruitmentRequestResponse> searchRecruitmentRequest(RecruitmentRequestSearchDTO searchDTO);

    public RecruitmentSearchCategoryDTO searchCategory();

}
