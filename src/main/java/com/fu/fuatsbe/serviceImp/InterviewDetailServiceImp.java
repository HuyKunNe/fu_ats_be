package com.fu.fuatsbe.serviceImp;

import com.fu.fuatsbe.DTO.InterviewDetailDTO;
import com.fu.fuatsbe.constant.cv.CVErrorMessage;
import com.fu.fuatsbe.constant.interview.InterviewErrorMessage;
import com.fu.fuatsbe.constant.interview_detail.InterviewDetailErrorMessage;
import com.fu.fuatsbe.constant.planDetail.PlanDetailErrorMessage;
import com.fu.fuatsbe.entity.CV;
import com.fu.fuatsbe.entity.Interview;
import com.fu.fuatsbe.entity.InterviewDetail;
import com.fu.fuatsbe.exceptions.ExistException;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.repository.CvRepository;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewDetailServiceImp implements InterviewDetailService {

        private final InterviewRepository interviewRepository;

        private final InterviewDetailRepository interviewDetailRepository;
        private final CvRepository cvRepository;
        private final ModelMapper modelMapper;

        @Override
        public ResponseWithTotalPage<InterviewDetailResponse> getAllInterviewDetail(int pageNo, int pageSize) {
                Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
                Page<InterviewDetail> interviewDetails = interviewDetailRepository.findAll(pageable);
                List<InterviewDetailResponse> list = new ArrayList<InterviewDetailResponse>();
                ResponseWithTotalPage<InterviewDetailResponse> result = new ResponseWithTotalPage<>();
                if (interviewDetails.hasContent()) {
                        for (InterviewDetail interviewDetail : interviewDetails.getContent()) {
                                InterviewDetailResponse interviewDetailResponse = modelMapper.map(interviewDetail,
                                                InterviewDetailResponse.class);
                                list.add(interviewDetailResponse);
                        }
                        result.setResponseList(list);
                        result.setTotalPage(interviewDetails.getTotalPages());
                } else
                        throw new ListEmptyException(PlanDetailErrorMessage.LIST_EMPTY_EXCEPTION);
                return result;
        }

        @Override
        public InterviewDetailResponse createInterviewDetail(InterviewDetailDTO interviewDetailDTO) {
                Interview interview = interviewRepository.findById(interviewDetailDTO.getInterviewID())
                                .orElseThrow(() -> new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));
                if (interviewDetailRepository.existsByInterviewId(interviewDetailDTO.getInterviewID())) {
                        throw new ExistException(InterviewDetailErrorMessage.INTERVIEW_DETAIL_OF_INTERVIEW_CREATED);
                }

                CV cv = cvRepository.findById(interview.getJobApply().getCv().getId())
                                .orElseThrow(() -> new NotFoundException(CVErrorMessage.NOT_FOUND));

                InterviewDetail interviewDetail = InterviewDetail.builder()
                                .startAt(Date.valueOf(interview.getDate().toLocalDateTime().toLocalDate()))
                                .endAt(interviewDetailDTO.getEnd())
                                .result(interviewDetailDTO.getResult())
                                .description(interviewDetailDTO.getDescription())
                                .recordMeeting(interviewDetailDTO.getRecordMeeting())
                                .interview(interview)
                                .build();
                if (interviewDetailDTO.getRecommendPositions().equalsIgnoreCase("")) {
                        cv.setRecommendPositions("N/A");
                } else {
                        cv.setRecommendPositions(interviewDetailDTO.getRecommendPositions());
                }
                cv.setNote(interviewDetailDTO.getNote());
                cvRepository.save(cv);

                InterviewDetail savedInterviewDetail = interviewDetailRepository.save(interviewDetail);
                InterviewDetailResponse interviewDetailResponse = modelMapper.map(savedInterviewDetail,
                                InterviewDetailResponse.class);
                return interviewDetailResponse;
        }

        @Override
        public InterviewDetailResponse getInterviewDetailById(int id) {
                InterviewDetail interviewDetail = interviewDetailRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException(
                                                InterviewDetailErrorMessage.INTERVIEW_DETAIL_NOT_FOUND));
                System.out.println("Check data " + interviewDetail.getDescription());
                InterviewDetailResponse interviewDetailResponse = modelMapper.map(interviewDetail,
                                InterviewDetailResponse.class);
                return interviewDetailResponse;
        }

        @Override
        public InterviewDetailResponse updateInterviewDetail(int id, InterviewDetailDTO interviewDetailDTO) {
                InterviewDetail interviewDetail = interviewDetailRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException(
                                                InterviewDetailErrorMessage.INTERVIEW_DETAIL_NOT_FOUND));
                Interview interview = interviewRepository.findById(interviewDetailDTO.getInterviewID())
                                .orElseThrow(() -> new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));

                CV cv = cvRepository.findById(interview.getJobApply().getCv().getId())
                                .orElseThrow(() -> new NotFoundException(CVErrorMessage.NOT_FOUND));

                cv.setRecommendPositions(interviewDetailDTO.getRecommendPositions());
                cv.setNote(interviewDetailDTO.getNote());

                interviewDetail.setStartAt(Date.valueOf(interview.getDate().toLocalDateTime().toLocalDate()));
                interviewDetail.setEndAt(interviewDetailDTO.getEnd());
                interviewDetail.setResult(interviewDetailDTO.getResult());
                interviewDetail.setDescription(interviewDetailDTO.getDescription());
                interviewDetail.setRecordMeeting(interviewDetailDTO.getRecordMeeting());
                interviewDetail.setInterview(interview);

                InterviewDetail savedInterviewDetail = interviewDetailRepository.save(interviewDetail);

                InterviewDetailResponse interviewDetailResponse = modelMapper.map(savedInterviewDetail,
                                InterviewDetailResponse.class);
                return interviewDetailResponse;
        }

        @Override
        public InterviewDetailResponse getInterviewDetailByInterviewId(int interviewId) {
                interviewRepository.findById(interviewId)
                                .orElseThrow(() -> new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));
                InterviewDetail interviewDetail = interviewDetailRepository.getInterviewDetailByInterviewId(interviewId)
                                .orElseThrow(() -> new NotFoundException(
                                                InterviewDetailErrorMessage.INTERVIEW_DETAIL_OF_INTERVIEW_NOT_FOUND));

                InterviewDetailResponse interviewDetailResponse = modelMapper.map(interviewDetail,
                                InterviewDetailResponse.class);
                return interviewDetailResponse;
        }

        @Override
        public ResponseWithTotalPage<InterviewDetailResponse> getAllInterviewDetailByDepartment(String departmentName,
                        int pageNo, int pageSize) {
                Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
                Page<InterviewDetail> interviewDetails = interviewDetailRepository
                                .getInterviewDetailByDepartment(departmentName, pageable);
                List<InterviewDetailResponse> list = new ArrayList<InterviewDetailResponse>();
                ResponseWithTotalPage<InterviewDetailResponse> result = new ResponseWithTotalPage<>();
                if (interviewDetails.hasContent()) {
                        for (InterviewDetail interviewDetail : interviewDetails) {
                                InterviewDetailResponse interviewDetailResponse = modelMapper.map(interviewDetail,
                                                InterviewDetailResponse.class);
                                list.add(interviewDetailResponse);
                        }
                        result.setResponseList(list);
                        result.setTotalPage(interviewDetails.getTotalPages());
                } else
                        throw new ListEmptyException(PlanDetailErrorMessage.LIST_EMPTY_EXCEPTION);
                return result;
        }
}
