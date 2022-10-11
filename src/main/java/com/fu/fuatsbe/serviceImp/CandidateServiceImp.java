package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.fu.fuatsbe.service.CandidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidateServiceImp implements CandidateService {

    private final ModelMapper modelMapper;
    private final CandidateRepository candidateRepository;

    @Override
    public List<CandidateResponseDTO> getAllCandidates(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Candidate> pageResult = candidateRepository.findAll(pageable);

        List<CandidateResponseDTO> result = new ArrayList<CandidateResponseDTO>();
        if (pageResult.hasContent()) {
            for (Candidate candidate : pageResult.getContent()) {
                CandidateResponseDTO candidateResponseDTO = modelMapper.map(candidate, CandidateResponseDTO.class);
                result.add(candidateResponseDTO);
            }
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
    public CandidateResponseDTO updateCandidateById(CandidateUpdateDTO updateDTO) {
        // TODO Auto-generated method stub
        return null;
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
    public List<CandidateResponseDTO> getActivateCandidates(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Candidate> pageResult = candidateRepository.findByStatus(CandidateStatus.ACTIVATED, pageable);

        List<CandidateResponseDTO> result = new ArrayList<CandidateResponseDTO>();
        if (pageResult.hasContent()) {
            for (Candidate candidate : pageResult.getContent()) {
                CandidateResponseDTO candidateResponseDTO = modelMapper.map(candidate, CandidateResponseDTO.class);
                result.add(candidateResponseDTO);
            }
        } else
            throw new ListEmptyException(CandidateErrorMessage.LIST_CANDIDATE_IS_EMPTY);
        return result;
    }

    @Override
    public List<CandidateResponseDTO> getDisableCandidates(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Candidate> pageResult = candidateRepository.findByStatus(CandidateStatus.DISABLED, pageable);

        List<CandidateResponseDTO> result = new ArrayList<CandidateResponseDTO>();
        if (pageResult.hasContent()) {
            for (Candidate candidate : pageResult.getContent()) {
                CandidateResponseDTO candidateResponseDTO = modelMapper.map(candidate, CandidateResponseDTO.class);
                result.add(candidateResponseDTO);
            }
        } else
            throw new ListEmptyException(CandidateErrorMessage.LIST_CANDIDATE_IS_EMPTY);
        return result;
    }

}
