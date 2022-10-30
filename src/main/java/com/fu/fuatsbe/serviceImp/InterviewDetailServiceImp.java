package com.fu.fuatsbe.serviceImp;

import com.fu.fuatsbe.DTO.InterviewDetailDTO;
import com.fu.fuatsbe.DTO.InterviewUpdateDTO;
import com.fu.fuatsbe.constant.interview.InterviewErrorMessage;
import com.fu.fuatsbe.constant.interview_detail.InterviewDetailErrorMessage;
import com.fu.fuatsbe.constant.planDetail.PlanDetailErrorMessage;
import com.fu.fuatsbe.entity.Interview;
import com.fu.fuatsbe.entity.InterviewDetail;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.repository.InterviewDetailRepository;
import com.fu.fuatsbe.repository.InterviewRepository;
import com.fu.fuatsbe.response.InterviewDetailResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.InterviewDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewDetailServiceImp implements InterviewDetailService {

    private final InterviewRepository interviewRepository;

    private final InterviewDetailRepository interviewDetailRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseWithTotalPage getAllInterviewDetail(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<InterviewDetail> interviewDetails = interviewDetailRepository.findAll(pageable);
        List<Object> result = new ArrayList<>();
        ResponseWithTotalPage response = null;
        if(interviewDetails.hasContent()){
            for (InterviewDetail interviewDetail: interviewDetails.getContent()) {
                InterviewDetailResponse interviewDetailResponse = modelMapper.map(interviewDetail, InterviewDetailResponse.class);
                result.add(interviewDetailResponse);
                response = ResponseWithTotalPage.builder()
                        .totalPage(interviewDetails.getTotalPages())
                        .responseList(result)
                        .build();
            }
        }else
            throw new ListEmptyException(PlanDetailErrorMessage.LIST_EMPTY_EXCEPTION);
        return response;
    }

    @Override
    public InterviewDetailResponse createInterviewDetail(InterviewDetailDTO interviewDetailDTO) {
        Interview interview = interviewRepository.findById(interviewDetailDTO.getInterviewID()).orElseThrow(() ->
                new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));
        InterviewDetail interviewDetail = InterviewDetail.builder()
                .startAt(interview.getDate())
                .endAt(interviewDetailDTO.getEnd())
                .result(interviewDetailDTO.getResult())
                .description(interviewDetailDTO.getDescription())
                .recordMeeting(interviewDetailDTO.getRecordMeeting())
                .interview(interview)
                .build();
        InterviewDetail savedInterviewDetail = interviewDetailRepository.save(interviewDetail);
        InterviewDetailResponse interviewDetailResponse = modelMapper.map(savedInterviewDetail, InterviewDetailResponse.class);
        return interviewDetailResponse;
    }

    @Override
    public InterviewDetailResponse getInterviewDetailById(int id) {
        InterviewDetail interviewDetail = interviewDetailRepository.findById(id).orElseThrow(() ->
                new NotFoundException(InterviewDetailErrorMessage.INTERVIEW_DETAIL_NOT_FOUND));
        System.out.println("Check data "+interviewDetail.getDescription());
        InterviewDetailResponse interviewDetailResponse = modelMapper.map(interviewDetail, InterviewDetailResponse.class);
        return interviewDetailResponse;
    }

    @Override
    public InterviewDetailResponse updateInterviewDetail(int id, InterviewDetailDTO interviewDetailDTO) {
        InterviewDetail interviewDetail = interviewDetailRepository.findById(id).orElseThrow(() ->
                new NotFoundException(InterviewDetailErrorMessage.INTERVIEW_DETAIL_NOT_FOUND));
        Interview interview = interviewRepository.findById(interviewDetailDTO.getInterviewID()).orElseThrow(() ->
                new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));

        interviewDetail.setStartAt(interview.getDate());
        interviewDetail.setEndAt(interviewDetailDTO.getEnd());
        interviewDetail.setResult(interviewDetailDTO.getResult());
        interviewDetail.setDescription(interviewDetailDTO.getDescription());
        interviewDetail.setRecordMeeting(interviewDetailDTO.getRecordMeeting());
        interviewDetail.setInterview(interview);

        InterviewDetail savedInterviewDetail = interviewDetailRepository.save(interviewDetail);

        InterviewDetailResponse interviewDetailResponse = modelMapper.map(savedInterviewDetail, InterviewDetailResponse.class);
        return interviewDetailResponse;
    }

    @Override
    public InterviewDetailResponse getInterviewDetailByInterviewId(int interviewId) {
        interviewRepository.findById(interviewId).orElseThrow(() -> new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));
        InterviewDetail interviewDetail = interviewDetailRepository.getInterviewDetailByInterviewId(interviewId).orElseThrow(()
        -> new NotFoundException(InterviewDetailErrorMessage.INTERVIEW_DETAIL_OF_INTERVIEW_NOT_FOUND));

        InterviewDetailResponse interviewDetailResponse = modelMapper.map(interviewDetail, InterviewDetailResponse.class);
        return interviewDetailResponse;
    }
}
