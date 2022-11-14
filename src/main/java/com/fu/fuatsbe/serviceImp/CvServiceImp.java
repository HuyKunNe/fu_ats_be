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
import com.fu.fuatsbe.constant.cv.CVStatus;
import com.fu.fuatsbe.constant.postion.PositionErrorMessage;
import com.fu.fuatsbe.entity.CV;
import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Position;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.repository.CandidateRepository;
import com.fu.fuatsbe.repository.CvRepository;
import com.fu.fuatsbe.repository.PositionRepository;
import com.fu.fuatsbe.response.CvResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.CVService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CvServiceImp implements CVService {

    private final ModelMapper modelMapper;
    private final CvRepository cvRepository;
    private final CandidateRepository candidateRepository;
    private final PositionRepository positionRepository;

    @Override
    public ResponseWithTotalPage<CvResponse> getAllCvs(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CV> pageResult = cvRepository.findAll(pageable);

        ResponseWithTotalPage<CvResponse> result = new ResponseWithTotalPage<>();
        List<CvResponse> list = new ArrayList<>();

        if (pageResult.hasContent()) {
            for (CV cv : pageResult.getContent()) {
                CvResponse cvResponse = modelMapper.map(cv, CvResponse.class);
                list.add(cvResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(CVErrorMessage.LIST_EMPTY);
        return result;
    }

    @Override
    public ResponseWithTotalPage<CvResponse> getAllCvByCandidate(int candidateId, int pageNo, int pageSize) {

        Optional<Candidate> candidate = candidateRepository.findById(candidateId);

        if (!candidate.isPresent()) {
            throw new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION);
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CV> pageResult = cvRepository.findByCandidate(candidate.get(), pageable);
        ResponseWithTotalPage<CvResponse> result = new ResponseWithTotalPage<>();
        List<CvResponse> list = new ArrayList<>();

        if (pageResult.hasContent()) {
            for (CV cv : pageResult.getContent()) {
                CvResponse cvResponse = modelMapper.map(cv, CvResponse.class);
                list.add(cvResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        }
        return result;
    }

    @Override
    public ResponseWithTotalPage<CvResponse> getAllSuitableCvs(int pageNo, int pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CvResponse createCV(CvCreateDTO createDTO) {
        Candidate candidate = candidateRepository.findById(createDTO.getCandidateId())
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));

        List<Position> listPositions = new ArrayList<Position>();

        if (createDTO.getPositionName().size() != 0) {
            for (String positionName : createDTO.getPositionName()) {
                Position position = positionRepository.findPositionByName(positionName)
                        .orElseThrow(() -> new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST));
                listPositions.add(position);
            }
        }

        CV cv = CV.builder().linkCV(createDTO.getLinkCV())
                .candidate(candidate).positions(listPositions)
                .title(createDTO.getTitle())
                .status(CVStatus.ACTIVE)
                .build();

        CV cvSaved = cvRepository.save(cv);

        CvResponse response = modelMapper.map(cvSaved, CvResponse.class);
        return response;
    }

    @Override
    public CvResponse updateCV(int id, CvUpdateDTO updateDTO) {
        CV cv = cvRepository.findById(id).orElseThrow(() -> new NotFoundException(CVErrorMessage.NOT_FOUND));
        List<Position> listPositions = new ArrayList<Position>();

        if (!updateDTO.getPositionName().isEmpty()) {
            for (String positionName : updateDTO.getPositionName()) {
                Position position = positionRepository.findPositionByName(positionName)
                        .orElseThrow(() -> new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST));
                listPositions.add(position);
            }
        }

        cv.setLinkCV(updateDTO.getLinkCV());
        cv.setPositions(listPositions);
        cv.setTitle(updateDTO.getTitle());
        CV cvSaved = cvRepository.save(cv);

        CvResponse response = modelMapper.map(cvSaved, CvResponse.class);
        return response;
    }

    @Override
    public CV deleteCV(int id) {
        CV cv = cvRepository.findById(id).orElseThrow(() -> new NotFoundException(CVErrorMessage.NOT_FOUND));
        cv.setStatus(CVStatus.DISABLE);
        return cvRepository.save(cv);
    }

    @Override
    public List<CvResponse> getRejectedCv() {
        List<CV> cvs = cvRepository.getRejectedCV();
        List<CvResponse> list = new ArrayList<>();
        for (CV cv : cvs) {
            CvResponse response = modelMapper.map(cv, CvResponse.class);
            list.add(response);
        }
        return list;
    }
}
