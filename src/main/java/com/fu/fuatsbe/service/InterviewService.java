package com.fu.fuatsbe.service;

import com.fu.fuatsbe.DTO.InterviewCreateDTO;
import com.fu.fuatsbe.entity.Interview;
import com.fu.fuatsbe.response.InterviewResponse;

import javax.mail.MessagingException;
import java.util.List;

public interface InterviewService {
public InterviewResponse createInterview(InterviewCreateDTO interviewCreateDTO) throws MessagingException;
public List<Interview> getAllInterview();

public InterviewResponse getInterviewByCandidateID(int candidateId);

}
