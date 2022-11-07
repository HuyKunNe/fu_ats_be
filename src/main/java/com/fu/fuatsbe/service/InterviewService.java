package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.InterviewCreateDTO;
import com.fu.fuatsbe.DTO.InterviewUpdateDTO;
import com.fu.fuatsbe.response.InterviewResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

import javax.mail.MessagingException;
import java.util.List;

public interface InterviewService {
    InterviewResponse createInterview(InterviewCreateDTO interviewCreateDTO) throws MessagingException;

    ResponseWithTotalPage<InterviewResponse> getAllInterview(int pageNo, int pageSize);

    List<InterviewResponse> getInterviewByCandidateID(int candidateId);

    List<InterviewResponse> getInterviewByEmployeeID(int employeeId);

    InterviewResponse getInterviewByID(int id);

    InterviewResponse updateInterview(int id, InterviewUpdateDTO interviewUpdateDTO) throws MessagingException;

    void closeInterview(int id);

    void cancelInterview(int id);

}
