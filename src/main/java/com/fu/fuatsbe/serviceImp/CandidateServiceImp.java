package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;

import com.fu.fuatsbe.response.IdAndNameResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.CandidateUpdateDTO;
import com.fu.fuatsbe.constant.account.AccountErrorMessage;
import com.fu.fuatsbe.constant.account.AccountStatus;
import com.fu.fuatsbe.constant.candidate.CandidateErrorMessage;
import com.fu.fuatsbe.constant.candidate.CandidateStatus;
import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.repository.CandidateRepository;
import com.fu.fuatsbe.response.CandidateResponseDTO;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.CandidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidateServiceImp implements CandidateService {

    private final ModelMapper modelMapper;
    private final CandidateRepository candidateRepository;

    @Override
    public ResponseWithTotalPage<CandidateResponseDTO> getAllCandidates(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Candidate> pageResult = candidateRepository.findAll(pageable);

        List<CandidateResponseDTO> list = new ArrayList<CandidateResponseDTO>();
        ResponseWithTotalPage<CandidateResponseDTO> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (Candidate candidate : pageResult.getContent()) {
                CandidateResponseDTO candidateResponseDTO = modelMapper.map(candidate, CandidateResponseDTO.class);
                list.add(candidateResponseDTO);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(CandidateErrorMessage.LIST_CANDIDATE_IS_EMPTY);
        return result;
    }

    @Override
    public CandidateResponseDTO getCandidateById(int id) {

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));
        CandidateResponseDTO candidateResponseDTO = modelMapper.map(candidate, CandidateResponseDTO.class);
        return candidateResponseDTO;

    }

    @Override
    public CandidateResponseDTO getCandidateByEmail(String email) {
        Candidate candidate = candidateRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));
        CandidateResponseDTO candidateResponseDTO = modelMapper.map(candidate, CandidateResponseDTO.class);
        return candidateResponseDTO;

    }

    @Override
    public CandidateResponseDTO getCandidateByPhone(String phone) {
        Candidate candidate = candidateRepository.findByPhone(phone)
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));
        CandidateResponseDTO candidateResponseDTO = modelMapper.map(candidate, CandidateResponseDTO.class);
        return candidateResponseDTO;
    }

    @Override
    public CandidateResponseDTO updateCandidateById(int id, CandidateUpdateDTO updateDTO) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));
        candidate.setName(updateDTO.getName());
        candidate.setPhone(updateDTO.getPhone());
        candidate.setImage(updateDTO.getImage());
        candidate.setGender(updateDTO.getGender());
        candidate.setDob(updateDTO.getDob());
        candidate.setAddress(updateDTO.getAddress());

        Candidate candidateSaved = candidateRepository.save(candidate);
        CandidateResponseDTO candidateResponseDTO = modelMapper.map(candidateSaved, CandidateResponseDTO.class);
        return candidateResponseDTO;
    }

    @Override
    public Candidate deleteCandidateById(int id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));
        if (candidate.getAccount().getStatus().equals(CandidateStatus.DISABLED)) {
            throw new NotFoundException(AccountErrorMessage.ACCOUNT_ALREADY_DELETED);
        }
        if (candidate.getStatus().equals(CandidateStatus.SUSPENDED)) {
            throw new NotFoundException(CandidateErrorMessage.CANDIDATE_ALREADY_SUSPENDED);
        }
        candidate.getAccount().setStatus(AccountStatus.DISABLED);
        candidate.setStatus(CandidateStatus.DISABLED);
        Candidate candidateSaved = candidateRepository.save(candidate);
        return candidateSaved;
    }

    @Override
    public ResponseWithTotalPage<CandidateResponseDTO> getActivateCandidates(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Candidate> pageResult = candidateRepository.findByStatus(CandidateStatus.ACTIVATED, pageable);

        List<CandidateResponseDTO> list = new ArrayList<CandidateResponseDTO>();
        ResponseWithTotalPage<CandidateResponseDTO> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (Candidate candidate : pageResult.getContent()) {
                CandidateResponseDTO candidateResponseDTO = modelMapper.map(candidate, CandidateResponseDTO.class);
                list.add(candidateResponseDTO);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(CandidateErrorMessage.LIST_CANDIDATE_IS_EMPTY);
        return result;
    }

    @Override
    public ResponseWithTotalPage<CandidateResponseDTO> getDisableCandidates(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Candidate> pageResult = candidateRepository.findByStatus(CandidateStatus.DISABLED, pageable);
        List<CandidateResponseDTO> list = new ArrayList<CandidateResponseDTO>();
        ResponseWithTotalPage<CandidateResponseDTO> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (Candidate candidate : pageResult.getContent()) {
                CandidateResponseDTO candidateResponseDTO = modelMapper.map(candidate, CandidateResponseDTO.class);
                list.add(candidateResponseDTO);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(CandidateErrorMessage.LIST_CANDIDATE_IS_EMPTY);
        return result;
    }
    @Override
    public List<IdAndNameResponse> getCandidateAppliedByRecruitment(int recruitmentId) {
        List<Candidate> candidates = candidateRepository.getCandidateAppliedByRecruitment(recruitmentId);
        List<IdAndNameResponse> responses = new ArrayList<>();
        for (Candidate candidate:candidates) {
            IdAndNameResponse idAndNameResponse = IdAndNameResponse.builder()
                    .id(candidate.getId())
                    .name(candidate.getName())
                    .build();
            responses.add(idAndNameResponse);
        }
        return responses;
    }

}
