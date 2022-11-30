package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.*;
import com.fu.fuatsbe.response.CountStatusResponse;
import com.fu.fuatsbe.response.IdAndNameResponse;
import com.fu.fuatsbe.response.RecruitmentRequestResponse;
import com.fu.fuatsbe.response.RecruitmentRequestResponseWithJobApply;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

public interface RecruitmentRequestService {
        public ResponseWithTotalPage<RecruitmentRequestResponse> getAllRecruitmentRequests(int pageNo, int pageSize);

        public RecruitmentRequestResponse getRecruitmentRequestById(int id);

        public ResponseWithTotalPage<RecruitmentRequestResponseWithJobApply> getAllOpenRecruitmentRequest(int pageNo,
                        int pageSize);

        public ResponseWithTotalPage<RecruitmentRequestResponse> getAllFilledRecruitmentRequest(int pageNo,
                        int pageSize);

        public ResponseWithTotalPage<RecruitmentRequestResponse> getAllClosedRecruitmentRequest(int pageNo,
                        int pageSize);

        public ResponseWithTotalPage<RecruitmentRequestResponse> getAllRecruitmentRequestByCreator(int id, int pageNo,
                        int pageSize);

        public RecruitmentRequestResponse updateRecruitmentRequest(int id, RecruitmentRequestUpdateDTO updateDTO);

        public RecruitmentRequestResponse createRecruitmentRequest(RecruitmentRequestCreateDTO createDTO);

        public RecruitmentRequestResponse closeRecruitmentRequest(int id);

        public List<RecruitmentRequestResponse> searchRecruitmentRequest(RecruitmentRequestSearchDTO searchDTO);

        public RecruitmentSearchCategoryDTO searchCategory();

        public List<RecruitmentRequestResponse> getNewestRecruitmentRequest();

        CountStatusResponse getStatusTotal();

        List<IdAndNameResponse> getIdAndNameRequestByDepartment(int departmentId);

        List<IdAndNameResponse> getAllActiveRequest();

        // public ResponseWithTotalPage<RecruitmentRequestResponse>
        // getExpiryDateRecruitmentRequest(int pageNo,
        // int pageSize);

        public List<RecruitmentRequestResponse> getExpiryDateRecruitmentRequest();

        public boolean closeListRecruitmentRequest(List<Integer> listId);
}
