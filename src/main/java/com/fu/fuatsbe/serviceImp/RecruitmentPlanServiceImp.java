package com.fu.fuatsbe.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.RecruimentPlanUpdateDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanActionDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanCreateDTO;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.recruitmentPlan.RecruitmentPlanErrorMessage;
import com.fu.fuatsbe.constant.recruitmentPlan.RecruitmentPlanStatus;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.RecruitmentPlan;
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
    public List<RecruitmentPlanResponse> getAllRecruitmentPlans() {
        List<RecruitmentPlan> list = recruitmentPlanRepository.findAll();
        List<RecruitmentPlanResponse> result = new ArrayList<RecruitmentPlanResponse>();
        if (list.size() > 0) {
            for (RecruitmentPlan recruitmentPlan : list) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                result.add(response);
            }
        }
        return result;
    }

    @Override
    public RecruitmentPlanResponse getRecruitmentPlanById(int id) {
        try {
            RecruitmentPlan recruitmentPlan = recruitmentPlanRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException(
                            RecruitmentPlanErrorMessage.RECRUITMENTPLAN_NOT_FOUND_EXCEPTION));
            RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<RecruitmentPlanResponse> getAllApprovedRecruitmentPlan() {
        try {
            List<RecruitmentPlan> list = recruitmentPlanRepository.findByStatus(RecruitmentPlanStatus.APPROVED);
            List<RecruitmentPlanResponse> result = new ArrayList<RecruitmentPlanResponse>();
            if (list.size() > 0) {
                for (RecruitmentPlan recruitmentPlan : list) {
                    RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                    result.add(response);
                }
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<RecruitmentPlanResponse> getAllCanceledRecruitmentPlans() {
        try {
            List<RecruitmentPlan> list = recruitmentPlanRepository.findByStatus(RecruitmentPlanStatus.CANCELED);
            List<RecruitmentPlanResponse> result = new ArrayList<RecruitmentPlanResponse>();
            if (list.size() > 0) {
                for (RecruitmentPlan recruitmentPlan : list) {
                    RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                    result.add(response);
                }
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<RecruitmentPlanResponse> getAllRejectedRecruitmentPlans() {
        try {
            List<RecruitmentPlan> list = recruitmentPlanRepository.findByStatus(RecruitmentPlanStatus.REJECTED);
            List<RecruitmentPlanResponse> result = new ArrayList<RecruitmentPlanResponse>();
            if (list.size() > 0) {
                for (RecruitmentPlan recruitmentPlan : list) {
                    RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                    result.add(response);
                }
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<RecruitmentPlanResponse> getAllPedingRecruitmentPlans() {
        try {
            List<RecruitmentPlan> list = recruitmentPlanRepository.findByStatus(RecruitmentPlanStatus.PENDING);
            List<RecruitmentPlanResponse> result = new ArrayList<RecruitmentPlanResponse>();
            if (list.size() > 0) {
                for (RecruitmentPlan recruitmentPlan : list) {
                    RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                    result.add(response);
                }
            }
            return result;
        } catch (Exception e) {
            return null;
        }
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
            RecruitmentPlan recruitmentPlan = RecruitmentPlan.builder().period(createDTO.getPeriod())
                    .amount(createDTO.getAmount()).status(RecruitmentPlanStatus.PENDING).creator(optionalCreator.get())
                    .build();
            recruitmentPlanRepository.save(recruitmentPlan);
            RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
            return response;
        } else {
            return null;
        }
    }

    @Override
    public RecruitmentPlanResponse approvedRecruitmentPlan(RecruitmentPlanActionDTO actionDTO) {
        try {
            RecruitmentPlan recruitmentPlan = recruitmentPlanRepository.findById(actionDTO.getId())
                    .orElseThrow(() -> new IllegalStateException(
                            RecruitmentPlanErrorMessage.RECRUITMENTPLAN_NOT_FOUND_EXCEPTION));
            if (recruitmentPlan != null) {
                Employee approver = employeeRepository.findById(actionDTO.getEmployeeId())
                        .orElseThrow(() -> new IllegalStateException(
                                EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
                if (approver != null) {
                    recruitmentPlan.setStatus(RecruitmentPlanStatus.APPROVED);
                    recruitmentPlan.setApprover(approver);
                    RecruitmentPlan recruitmentPlanSaved = recruitmentPlanRepository.save(recruitmentPlan);
                    RecruitmentPlanResponse response = modelMapper.map(recruitmentPlanSaved,
                            RecruitmentPlanResponse.class);
                    return response;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<RecruitmentPlanResponse> getAllRecruitmentPlansByApprover(int approverId) {
        try {
            List<RecruitmentPlan> list = recruitmentPlanRepository.findByApproverId(approverId);
            List<RecruitmentPlanResponse> result = new ArrayList<RecruitmentPlanResponse>();
            if (list.size() > 0) {
                for (RecruitmentPlan recruitmentPlan : list) {
                    RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                    result.add(response);
                }
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<RecruitmentPlanResponse> getAllRecruitmentPlansByCreator(int creatorId) {
        try {
            List<RecruitmentPlan> list = recruitmentPlanRepository.findByCreatorId(creatorId);
            List<RecruitmentPlanResponse> result = new ArrayList<RecruitmentPlanResponse>();
            if (list.size() > 0) {
                for (RecruitmentPlan recruitmentPlan : list) {
                    RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                    result.add(response);
                }
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public RecruitmentPlanResponse canceledRecruitmentPlan(RecruitmentPlanActionDTO actionDTO) {
        try {
            RecruitmentPlan recruitmentPlan = recruitmentPlanRepository.findById(actionDTO.getId())
                    .orElseThrow(() -> new IllegalStateException(
                            RecruitmentPlanErrorMessage.RECRUITMENTPLAN_NOT_FOUND_EXCEPTION));
            if (recruitmentPlan != null) {
                Employee approver = employeeRepository.findById(actionDTO.getEmployeeId())
                        .orElseThrow(() -> new IllegalStateException(
                                EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
                if (approver != null) {
                    recruitmentPlan.setStatus(RecruitmentPlanStatus.CANCELED);
                    recruitmentPlan.setApprover(approver);
                    RecruitmentPlan recruitmentPlanSaved = recruitmentPlanRepository.save(recruitmentPlan);
                    RecruitmentPlanResponse response = modelMapper.map(recruitmentPlanSaved,
                            RecruitmentPlanResponse.class);
                    return response;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public RecruitmentPlanResponse rejectedRecruitmentPlan(RecruitmentPlanActionDTO actionDTO) {
        try {
            RecruitmentPlan recruitmentPlan = recruitmentPlanRepository.findById(actionDTO.getId())
                    .orElseThrow(() -> new IllegalStateException(
                            RecruitmentPlanErrorMessage.RECRUITMENTPLAN_NOT_FOUND_EXCEPTION));
            if (recruitmentPlan != null) {
                Employee approver = employeeRepository.findById(actionDTO.getEmployeeId())
                        .orElseThrow(() -> new IllegalStateException(
                                EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
                if (approver != null) {
                    recruitmentPlan.setStatus(RecruitmentPlanStatus.REJECTED);
                    recruitmentPlan.setApprover(approver);
                    RecruitmentPlan recruitmentPlanSaved = recruitmentPlanRepository.save(recruitmentPlan);
                    RecruitmentPlanResponse response = modelMapper.map(recruitmentPlanSaved,
                            RecruitmentPlanResponse.class);
                    return response;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
