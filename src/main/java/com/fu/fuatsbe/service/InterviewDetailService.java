package com.fu.fuatsbe.service;

import java.util.List;

import com.fu.fuatsbe.DTO.InterviewDetailDTO;
import com.fu.fuatsbe.response.InterviewDetailResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

public interface InterviewDetailService {
    ResponseWithTotalPage<InterviewDetailResponse> getAllInterviewDetail(int pageNo, int pageSize);

    InterviewDetailResponse createInterviewDetail(InterviewDetailDTO interviewDetailDTO);

    InterviewDetailResponse getInterviewDetailById(int id);

    InterviewDetailResponse updateInterviewDetail(int id, InterviewDetailDTO interviewDetailDTO);

    InterviewDetailResponse getInterviewDetailByInterviewId(int interviewId);

    List<InterviewDetailResponse> getAllInterviewDetailByDepartment(String departmentName);
}
