package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.InterviewCreateDTO;
import com.fu.fuatsbe.entity.Interview;
import com.fu.fuatsbe.response.InterviewResponse;

import javax.mail.MessagingException;
import java.util.List;

public interface InterviewService {
 InterviewResponse createInterview(InterviewCreateDTO interviewCreateDTO) throws MessagingException;
 List<Interview> getAllInterview();

 List<InterviewResponse> getInterviewByCandidateID(int candidateId);

 InterviewResponse updateInterview();

 void closeInterview(int id);

}
