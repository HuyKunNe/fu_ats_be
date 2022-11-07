package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.*;
import com.fu.fuatsbe.response.RecruitmentRequestResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

public interface RecruitmentRequestService {
    public ResponseWithTotalPage<RecruitmentRequestResponse> getAllRecruitmentRequests(int pageNo, int pageSize);

    public RecruitmentRequestResponse getRecruitmentRequestById(int id);

    public RecruitmentRequestResponseWithTotalPages getAllOpenRecruitmentRequest(int pageNo, int pageSize);

    public RecruitmentRequestResponseWithTotalPages getAllFilledRecruitmentRequest(int pageNo, int pageSize);

    public RecruitmentRequestResponseWithTotalPages getAllClosedRecruitmentRequest(int pageNo, int pageSize);

    public RecruitmentRequestResponseWithTotalPages getAllRecruitmentRequestByCreator(int id, int pageNo, int pageSize);

    public RecruitmentRequestResponse updateRecruitmentRequest(int id, RecruitmentRequestUpdateDTO updateDTO);

    public RecruitmentRequestResponse createRecruitmentRequest(RecruitmentRequestCreateDTO createDTO);

    public RecruitmentRequestResponse closeRecruitmentRequest(int id);

    public List<RecruitmentRequestResponse> searchRecruitmentRequest(RecruitmentRequestSearchDTO searchDTO);

    public RecruitmentSearchCategoryDTO searchCategory();

}
