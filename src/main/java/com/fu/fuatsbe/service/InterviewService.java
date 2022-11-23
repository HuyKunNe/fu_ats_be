package com.fu.fuatsbe.service;

import java.util.List;

import javax.mail.MessagingException;

import com.fu.fuatsbe.DTO.CancelInterviewDTO;
import com.fu.fuatsbe.DTO.InterviewCreateDTO;
import com.fu.fuatsbe.DTO.InterviewUpdateDTO;
import com.fu.fuatsbe.response.InterviewResponse;
import com.fu.fuatsbe.response.NameAndStatusResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;

public interface InterviewService {
    InterviewResponse createInterview(InterviewCreateDTO interviewCreateDTO) throws MessagingException;

    ResponseWithTotalPage<InterviewResponse> getAllInterview(int pageNo, int pageSize);

    ResponseWithTotalPage<InterviewResponse> getInterviewByCandidateID(int candidateId, int pageNo, int pageSize);

    ResponseWithTotalPage<InterviewResponse> getInterviewByEmployeeID(int employeeId, int pageNo, int pageSize);

    InterviewResponse getInterviewByID(int id);

    InterviewResponse updateInterview(int id, InterviewUpdateDTO interviewUpdateDTO) throws MessagingException;

    void closeInterview(int id);

    void cancelInterview(CancelInterviewDTO cancelInterviewDTO) throws MessagingException;

    void confirmJoinInterviewByEmployee(int idInterview, int idEmployee);

    void rejectJoinInterviewByEmployee(int idInterview, int idEmployee);

    void confirmJoinInterviewByCandidate(int idInterview, int idCandidate);

    void rejectJoinInterviewByCandidate(int idInterview, int idCandidate) throws MessagingException;

    ResponseWithTotalPage<InterviewResponse> getInterviewByDepartment(int departmentId, int pageNo, int pageSize);

    List<InterviewResponse> searchInterview(String candidateName, String type, String status, String date,
            String round);
    List<NameAndStatusResponse> getNameAndStatusByInterviewId(int interviewId);

}
