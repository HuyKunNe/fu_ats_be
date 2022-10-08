package com.fu.fuatsbe.serviceImp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.PlanDetailActionDTO;
import com.fu.fuatsbe.DTO.PlanDetailCreateDTO;
import com.fu.fuatsbe.DTO.PlanDetailUpdateDTO;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.planDetail.PlanDetailErrorMessage;
import com.fu.fuatsbe.constant.planDetail.PlanDetailStatus;
import com.fu.fuatsbe.constant.postion.PositionErrorMessage;
import com.fu.fuatsbe.constant.recruitmentPlan.RecruitmentPlanErrorMessage;
import com.fu.fuatsbe.constant.recruitmentPlan.RecruitmentPlanStatus;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.PlanDetail;
import com.fu.fuatsbe.entity.Position;
import com.fu.fuatsbe.entity.RecruitmentPlan;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.exceptions.NotValidException;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.PlanDetailRepository;
import com.fu.fuatsbe.repository.PositionRepository;
import com.fu.fuatsbe.repository.RecruitmentPlanRepository;
import com.fu.fuatsbe.response.PlanDetailResponseDTO;
import com.fu.fuatsbe.service.PlanDetailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanDetailServiceImpl implements PlanDetailService {

    private final ModelMapper modelMapper;
    private final PlanDetailRepository planDetailRepository;
    private final RecruitmentPlanRepository recruitmentPlanRepository;
    private final PositionRepository positionRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<PlanDetailResponseDTO> getAllPlanDetails() {
        List<PlanDetail> list = planDetailRepository.findAll();
        List<PlanDetailResponseDTO> result = new ArrayList<PlanDetailResponseDTO>();
        if (list.size() > 0) {
            for (PlanDetail planDetail : list) {
                PlanDetailResponseDTO planDetailResponse = modelMapper.map(planDetail, PlanDetailResponseDTO.class);
                result.add(planDetailResponse);
            }
        } else
            throw new ListEmptyException(PlanDetailErrorMessage.LIST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public List<PlanDetailResponseDTO> getAllByRecruitmentPlans(int recruitmentPlanId) {
        Optional<RecruitmentPlan> optionalRecruitmentPlan = recruitmentPlanRepository.findById(recruitmentPlanId);
        if (!optionalRecruitmentPlan.isPresent())
            throw new NotFoundException(RecruitmentPlanErrorMessage.RECRUITMENTPLAN_NOT_FOUND_EXCEPTION);
        List<PlanDetail> list = planDetailRepository.findByRecruitmentPlan(optionalRecruitmentPlan.get());
        List<PlanDetailResponseDTO> result = new ArrayList<PlanDetailResponseDTO>();
        if (list.size() > 0) {
            for (PlanDetail planDetail : list) {
                PlanDetailResponseDTO planDetailResponse = modelMapper.map(planDetail, PlanDetailResponseDTO.class);
                result.add(planDetailResponse);
            }
        } else
            throw new ListEmptyException(PlanDetailErrorMessage.LIST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public PlanDetailResponseDTO getPlanDetailById(int id) {
        PlanDetail planDetail = planDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PlanDetailErrorMessage.PLAN_DETAIL_NOT_FOUND_EXCEPTION));
        PlanDetailResponseDTO planDetailResponse = modelMapper.map(planDetail, PlanDetailResponseDTO.class);
        return planDetailResponse;
    }

    @Override
    public PlanDetailResponseDTO updatePlanDetailById(int id, PlanDetailUpdateDTO updateDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PlanDetailResponseDTO createPlanDetail(PlanDetailCreateDTO createDTO) {
        Optional<Position> optionalPosition = positionRepository.findById(createDTO.getPositionId());
        if (!optionalPosition.isPresent())
            throw new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST);

        Optional<RecruitmentPlan> optionalRecruitmentPlan = recruitmentPlanRepository
                .findById(createDTO.getRecruitmentPlanId());
        if (!optionalRecruitmentPlan.isPresent())
            throw new NotFoundException(RecruitmentPlanErrorMessage.RECRUITMENTPLAN_NOT_FOUND_EXCEPTION);
        if (!optionalRecruitmentPlan.get().getStatus().equalsIgnoreCase(RecruitmentPlanStatus.APPROVED))
            throw new NotValidException(RecruitmentPlanErrorMessage.ECRUITMENTPLAN_NOT_APPROVED_EXCEPTION);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDate dateFormat = LocalDate.parse(date.toString(), format);

        PlanDetail planDetail = PlanDetail.builder().amount(createDTO.getAmount()).skills(createDTO.getSkills())
                .date(Date.valueOf(dateFormat))
                .position(optionalPosition.get()).recruitmentPlan(optionalRecruitmentPlan.get())
                .status(PlanDetailStatus.PENDING).build();
        PlanDetail planDetailSaved = planDetailRepository.save(planDetail);
        PlanDetailResponseDTO responseDTO = modelMapper.map(planDetailSaved, PlanDetailResponseDTO.class);
        return responseDTO;
    }

    @Override
    public List<PlanDetailResponseDTO> getPendingPlanDetails() {
        List<PlanDetail> list = planDetailRepository.findByStatus(PlanDetailStatus.PENDING);
        List<PlanDetailResponseDTO> result = new ArrayList<PlanDetailResponseDTO>();
        if (list.size() > 0) {
            for (PlanDetail planDetail : list) {
                PlanDetailResponseDTO planDetailResponse = modelMapper.map(planDetail, PlanDetailResponseDTO.class);
                result.add(planDetailResponse);
            }
        } else
            throw new ListEmptyException(PlanDetailErrorMessage.LIST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public List<PlanDetailResponseDTO> getApprovedPlanDetails() {
        List<PlanDetail> list = planDetailRepository.findByStatus(PlanDetailStatus.APPROVED);
        List<PlanDetailResponseDTO> result = new ArrayList<PlanDetailResponseDTO>();
        if (list.size() > 0) {
            for (PlanDetail planDetail : list) {
                PlanDetailResponseDTO planDetailResponse = modelMapper.map(planDetail, PlanDetailResponseDTO.class);
                result.add(planDetailResponse);
            }
        } else
            throw new ListEmptyException(PlanDetailErrorMessage.LIST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public List<PlanDetailResponseDTO> getCanceledPlanDetails() {
        List<PlanDetail> list = planDetailRepository.findByStatus(PlanDetailStatus.CANCELED);
        List<PlanDetailResponseDTO> result = new ArrayList<PlanDetailResponseDTO>();
        if (list.size() > 0) {
            for (PlanDetail planDetail : list) {
                PlanDetailResponseDTO planDetailResponse = modelMapper.map(planDetail, PlanDetailResponseDTO.class);
                result.add(planDetailResponse);
            }
        } else
            throw new ListEmptyException(PlanDetailErrorMessage.LIST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public PlanDetailResponseDTO approvedPlanDetails(PlanDetailActionDTO actionDTO) {
        PlanDetail planDetail = planDetailRepository.findById(actionDTO.getId())
                .orElseThrow(() -> new NotFoundException(
                        PlanDetailErrorMessage.PLAN_DETAIL_NOT_FOUND_EXCEPTION));
        Employee approver = employeeRepository.findById(actionDTO.getEmployeeId())
                .orElseThrow(() -> new NotFoundException(
                        EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
        planDetail.setStatus(PlanDetailStatus.APPROVED);
        planDetail.setApprover(approver);
        PlanDetail planDetailSaved = planDetailRepository.save(planDetail);
        PlanDetailResponseDTO response = modelMapper.map(planDetailSaved,
                PlanDetailResponseDTO.class);
        return response;
    }

    @Override
    public PlanDetailResponseDTO canceledPlanDetails(PlanDetailActionDTO actionDTO) {
        PlanDetail planDetail = planDetailRepository.findById(actionDTO.getId())
                .orElseThrow(() -> new NotFoundException(
                        PlanDetailErrorMessage.PLAN_DETAIL_NOT_FOUND_EXCEPTION));
        Employee approver = employeeRepository.findById(actionDTO.getEmployeeId())
                .orElseThrow(() -> new NotFoundException(
                        EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
        planDetail.setStatus(PlanDetailStatus.CANCELED);
        planDetail.setApprover(approver);
        PlanDetail planDetailSaved = planDetailRepository.save(planDetail);
        PlanDetailResponseDTO response = modelMapper.map(planDetailSaved,
                PlanDetailResponseDTO.class);
        return response;
    }

}
