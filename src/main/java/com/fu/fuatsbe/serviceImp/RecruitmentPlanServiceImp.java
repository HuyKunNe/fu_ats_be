package com.fu.fuatsbe.serviceImp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.RecruimentPlanUpdateDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanActionDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanCreateDTO;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.recruitmentPlan.RecruitmentPlanErrorMessage;
import com.fu.fuatsbe.constant.recruitmentPlan.RecruitmentPlanStatus;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.RecruitmentPlan;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.RecruitmentPlanRepository;
import com.fu.fuatsbe.response.RecruitmentPlanResponse;
import com.fu.fuatsbe.service.RecruitmentPlanService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentPlanServiceImp implements RecruitmentPlanService {

    private final RecruitmentPlanRepository recruitmentPlanRepository;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<RecruitmentPlanResponse> getAllRecruitmentPlans(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findAll(pageable);
        List<RecruitmentPlanResponse> result = new ArrayList<RecruitmentPlanResponse>();

        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                result.add(response);
            }
        } else
            throw new ListEmptyException(RecruitmentPlanErrorMessage.LIST_RECRUITMENTPLAN_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public RecruitmentPlanResponse getRecruitmentPlanById(int id) {

        RecruitmentPlan recruitmentPlan = recruitmentPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        RecruitmentPlanErrorMessage.RECRUITMENTPLAN_NOT_FOUND_EXCEPTION));
        RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
        return response;

    }

    @Override
    public List<RecruitmentPlanResponse> getAllApprovedRecruitmentPlan(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findByStatus(RecruitmentPlanStatus.APPROVED,
                pageable);
        List<RecruitmentPlanResponse> result = new ArrayList<RecruitmentPlanResponse>();
        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                result.add(response);
            }
        } else
            throw new ListEmptyException(RecruitmentPlanErrorMessage.LIST_RECRUITMENTPLAN_EMPTY_EXCEPTION);
        return result;

    }

    @Override
    public List<RecruitmentPlanResponse> getAllCanceledRecruitmentPlans(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findByStatus(RecruitmentPlanStatus.CANCELED,
                pageable);
        List<RecruitmentPlanResponse> result = new ArrayList<RecruitmentPlanResponse>();
        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                result.add(response);
            }
        } else
            throw new ListEmptyException(RecruitmentPlanErrorMessage.LIST_RECRUITMENTPLAN_EMPTY_EXCEPTION);
        return result;

    }

    @Override
    public List<RecruitmentPlanResponse> getAllRejectedRecruitmentPlans(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findByStatus(RecruitmentPlanStatus.REJECTED,
                pageable);
        List<RecruitmentPlanResponse> result = new ArrayList<RecruitmentPlanResponse>();
        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                result.add(response);
            }
        } else
            throw new ListEmptyException(RecruitmentPlanErrorMessage.LIST_RECRUITMENTPLAN_EMPTY_EXCEPTION);
        return result;

    }

    @Override
    public List<RecruitmentPlanResponse> getAllPedingRecruitmentPlans(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findByStatus(RecruitmentPlanStatus.PENDING,
                pageable);
        List<RecruitmentPlanResponse> result = new ArrayList<RecruitmentPlanResponse>();
        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                result.add(response);
            }
        } else
            throw new ListEmptyException(RecruitmentPlanErrorMessage.LIST_RECRUITMENTPLAN_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public RecruitmentPlanResponse updateRecruitmentPlan(int id, RecruimentPlanUpdateDTO updateDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RecruitmentPlanResponse createRecruitmentPlan(RecruitmentPlanCreateDTO createDTO) {
        Optional<Employee> optionalCreator = employeeRepository.findById(createDTO.getCreatorId());
        if (optionalCreator.isPresent()) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate periodFrom = LocalDate.parse(createDTO.getPeriodFrom().toString(), format);
            LocalDate periodTo = LocalDate.parse(createDTO.getPeriodTo().toString(), format);

            RecruitmentPlan recruitmentPlan = RecruitmentPlan.builder().periodFrom(Date.valueOf(periodFrom))
                    .periodTo(Date.valueOf(periodTo))
                    .amount(createDTO.getAmount()).status(RecruitmentPlanStatus.PENDING).creator(optionalCreator.get())
                    .build();
            recruitmentPlanRepository.save(recruitmentPlan);
            RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
            return response;
        } else {
            throw new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    public RecruitmentPlanResponse approvedRecruitmentPlan(RecruitmentPlanActionDTO actionDTO) {
        RecruitmentPlan recruitmentPlan = recruitmentPlanRepository.findById(actionDTO.getId())
                .orElseThrow(() -> new NotFoundException(
                        RecruitmentPlanErrorMessage.RECRUITMENTPLAN_NOT_FOUND_EXCEPTION));
        Employee approver = employeeRepository.findById(actionDTO.getEmployeeId())
                .orElseThrow(() -> new NotFoundException(
                        EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
        recruitmentPlan.setStatus(RecruitmentPlanStatus.APPROVED);
        recruitmentPlan.setApprover(approver);
        RecruitmentPlan recruitmentPlanSaved = recruitmentPlanRepository.save(recruitmentPlan);
        RecruitmentPlanResponse response = modelMapper.map(recruitmentPlanSaved,
                RecruitmentPlanResponse.class);
        return response;

    }

    @Override
    public List<RecruitmentPlanResponse> getAllRecruitmentPlansByApprover(int approverId, int pageNo, int pageSize) {

        Employee approver = employeeRepository.findById(approverId)
                .orElseThrow(() -> new NotFoundException(
                        EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findByApprover(approver,
                pageable);
        List<RecruitmentPlanResponse> result = new ArrayList<RecruitmentPlanResponse>();

        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                result.add(response);
            }
        } else
            throw new ListEmptyException(RecruitmentPlanErrorMessage.LIST_RECRUITMENTPLAN_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public List<RecruitmentPlanResponse> getAllRecruitmentPlansByCreator(int creatorId, int pageNo, int pageSize) {

        Employee approver = employeeRepository.findById(creatorId)
                .orElseThrow(() -> new NotFoundException(
                        EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findByApprover(approver,
                pageable);
        List<RecruitmentPlanResponse> result = new ArrayList<RecruitmentPlanResponse>();

        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                result.add(response);
            }
        } else
            throw new ListEmptyException(RecruitmentPlanErrorMessage.LIST_RECRUITMENTPLAN_EMPTY_EXCEPTION);
        return result;

    }

    @Override
    public RecruitmentPlanResponse canceledRecruitmentPlan(RecruitmentPlanActionDTO actionDTO) {
        RecruitmentPlan recruitmentPlan = recruitmentPlanRepository.findById(actionDTO.getId())
                .orElseThrow(() -> new NotFoundException(
                        RecruitmentPlanErrorMessage.RECRUITMENTPLAN_NOT_FOUND_EXCEPTION));
        Employee approver = employeeRepository.findById(actionDTO.getEmployeeId())
                .orElseThrow(() -> new NotFoundException(
                        EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
        recruitmentPlan.setStatus(RecruitmentPlanStatus.CANCELED);
        recruitmentPlan.setApprover(approver);
        RecruitmentPlan recruitmentPlanSaved = recruitmentPlanRepository.save(recruitmentPlan);
        RecruitmentPlanResponse response = modelMapper.map(recruitmentPlanSaved,
                RecruitmentPlanResponse.class);
        return response;

    }

    @Override
    public RecruitmentPlanResponse rejectedRecruitmentPlan(RecruitmentPlanActionDTO actionDTO) {

        RecruitmentPlan recruitmentPlan = recruitmentPlanRepository.findById(actionDTO.getId())
                .orElseThrow(() -> new NotFoundException(
                        RecruitmentPlanErrorMessage.RECRUITMENTPLAN_NOT_FOUND_EXCEPTION));
        Employee approver = employeeRepository.findById(actionDTO.getEmployeeId())
                .orElseThrow(() -> new NotFoundException(
                        EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
        recruitmentPlan.setStatus(RecruitmentPlanStatus.REJECTED);
        recruitmentPlan.setApprover(approver);
        RecruitmentPlan recruitmentPlanSaved = recruitmentPlanRepository.save(recruitmentPlan);
        RecruitmentPlanResponse response = modelMapper.map(recruitmentPlanSaved,
                RecruitmentPlanResponse.class);
        return response;

    }

}
