package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.CvCreateDTO;
import com.fu.fuatsbe.DTO.CvUpdateDTO;
import com.fu.fuatsbe.constant.candidate.CandidateErrorMessage;
import com.fu.fuatsbe.constant.cv.CVErrorMessage;
import com.fu.fuatsbe.entity.CV;
import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.repository.CandidateRepository;
import com.fu.fuatsbe.repository.CvRepository;
import com.fu.fuatsbe.response.CvResponse;
import com.fu.fuatsbe.service.CVService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CvServiceImp implements CVService {

    private final ModelMapper modelMapper;
    private final CvRepository cvRepository;
    private final CandidateRepository candidateRepository;

    @Override
    public List<CvResponse> getAllCvs(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CV> pageResult = cvRepository.findAll(pageable);

        List<CvResponse> result = new ArrayList<CvResponse>();
        if (pageResult.hasContent()) {
            for (CV cv : pageResult.getContent()) {
                CvResponse cvResponse = modelMapper.map(cv, CvResponse.class);
                result.add(cvResponse);
            }
        } else
            throw new ListEmptyException(CVErrorMessage.LIST_EMPTY);
        return result;
    }

    @Override
    public List<CvResponse> getAllCvByCandidates(int candidateId, int pageNo, int pageSize) {

        Optional<Candidate> candidate = candidateRepository.findById(candidateId);

        if (!candidate.isPresent()) {
            throw new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION);
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CV> pageResult = cvRepository.findByCandidate(candidate.get(), pageable);
        List<CvResponse> result = new ArrayList<CvResponse>();
        if (pageResult.hasContent()) {
            for (CV cv : pageResult.getContent()) {
                CvResponse cvResponse = modelMapper.map(cv, CvResponse.class);
                result.add(cvResponse);
            }
        } else
            throw new ListEmptyException(CVErrorMessage.LIST_EMPTY);
        return result;
    }

    @Override
    public List<CvResponse> getAllSuitableCvs(int pageNo, int pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CvResponse createCV(CvCreateDTO createDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CvResponse updateCV(CvUpdateDTO updateDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CV deleteCV(int id) {
        // TODO Auto-generated method stub
        return null;
    }

}
