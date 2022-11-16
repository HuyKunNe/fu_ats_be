package com.fu.fuatsbe.serviceImp;

import com.fu.fuatsbe.DTO.PlanDetailActionDTO;
import com.fu.fuatsbe.DTO.PlanDetailCreateDTO;
import com.fu.fuatsbe.DTO.PlanDetailUpdateDTO;
import com.fu.fuatsbe.constant.department.DepartmentErrorMessage;
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
import com.fu.fuatsbe.repository.*;
import com.fu.fuatsbe.response.*;
import com.fu.fuatsbe.service.PlanDetailService;
import com.fu.fuatsbe.service.RecruitmentPlanService;
import com.fu.fuatsbe.service.RecruitmentRequestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanDetailServiceImpl implements PlanDetailService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final PlanDetailRepository planDetailRepository;
    private final RecruitmentPlanRepository recruitmentPlanRepository;
    private final RecruitmentPlanService recruitmentPlanService;
    private final RecruitmentRequestService recruitmentRequestService;
    private final PositionRepository positionRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public ResponseWithTotalPage<PlanDetailResponseDTO> getAllPlanDetails(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<PlanDetail> pageResult = planDetailRepository.findAll(pageable);
        List<PlanDetailResponseDTO> list = new ArrayList<PlanDetailResponseDTO>();
        ResponseWithTotalPage<PlanDetailResponseDTO> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (PlanDetail planDetail : pageResult.getContent()) {
                PlanDetailResponseDTO planDetailResponse = modelMapper.map(planDetail, PlanDetailResponseDTO.class);
                list.add(planDetailResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(PlanDetailErrorMessage.LIST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public ResponseWithTotalPage<PlanDetailResponseDTO> getAllByRecruitmentPlans(int recruitmentPlanId, int pageNo,
            int pageSize) {
        Optional<RecruitmentPlan> optionalRecruitmentPlan = recruitmentPlanRepository.findById(recruitmentPlanId);
        if (!optionalRecruitmentPlan.isPresent())
            throw new NotFoundException(RecruitmentPlanErrorMessage.RECRUITMENTPLAN_NOT_FOUND_EXCEPTION);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<PlanDetail> pageResult = planDetailRepository.findByRecruitmentPlan(optionalRecruitmentPlan.get(),
                pageable);
        List<PlanDetailResponseDTO> list = new ArrayList<PlanDetailResponseDTO>();
        ResponseWithTotalPage<PlanDetailResponseDTO> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (PlanDetail planDetail : pageResult.getContent()) {
                PlanDetailResponseDTO planDetailResponse = modelMapper.map(planDetail, PlanDetailResponseDTO.class);
                list.add(planDetailResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
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
        PlanDetail planDetail = planDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PlanDetailErrorMessage.PLAN_DETAIL_NOT_FOUND_EXCEPTION));
        Position position = positionRepository.findById(updateDTO.getPositionId())
                .orElseThrow(() -> new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST));

        int totalAmount = planDetailRepository
                .totalAmount(planDetail.getRecruitmentPlan().getId());

        if (updateDTO.getAmount() > (planDetail.getRecruitmentPlan().getAmount() -
                totalAmount)) {
            throw new NotValidException(PlanDetailErrorMessage.NOT_VALID_AMOUNT_EXCEPTION);
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate periodFrom = LocalDate.parse(updateDTO.getPeriodFrom().toString(), format);
        LocalDate periodTo = LocalDate.parse(updateDTO.getPeriodTo().toString(), format);

        LocalDate endDateOfRecruitmentPlan = LocalDate.parse(planDetail.getRecruitmentPlan().getPeriodTo().toString(),
                format);

        if (periodTo.isAfter(endDateOfRecruitmentPlan)) {
            throw new NotValidException(PlanDetailErrorMessage.NOT_VALID_PERIOD_TO_EXCEPTION);
        }

        planDetail.setAmount(updateDTO.getAmount());
        planDetail.setSalary(updateDTO.getSalary());
        planDetail.setName(updateDTO.getName());
        planDetail.setReason(updateDTO.getReason());
        planDetail.setPeriodFrom(Date.valueOf(periodFrom));
        planDetail.setPeriodTo(Date.valueOf(periodTo));
        planDetail.setDescription(updateDTO.getNote());
        planDetail.setPosition(position);

        PlanDetail savedPlan = planDetailRepository.save(planDetail);
        PlanDetailResponseDTO planDetailResponse = modelMapper.map(savedPlan, PlanDetailResponseDTO.class);
        return planDetailResponse;
    }

    @Override
    public PlanDetailResponseDTO createPlanDetail(PlanDetailCreateDTO createDTO) {

        Optional<Position> optionalPosition = positionRepository.findPositionByName(createDTO.getPositionName());
        if (!optionalPosition.isPresent())
            throw new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST);

        Optional<RecruitmentPlan> optionalRecruitmentPlan = recruitmentPlanRepository
                .findById(createDTO.getRecruitmentPlanId());

        Employee creator = employeeRepository.findById(createDTO.getCreatorId())
                .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));

        if (!optionalRecruitmentPlan.isPresent())
            throw new NotFoundException(RecruitmentPlanErrorMessage.RECRUITMENTPLAN_NOT_FOUND_EXCEPTION);
        if (!optionalRecruitmentPlan.get().getStatus().equalsIgnoreCase(RecruitmentPlanStatus.APPROVED))
            throw new NotValidException(RecruitmentPlanErrorMessage.ECRUITMENTPLAN_NOT_APPROVED_EXCEPTION);

        int totalAmount = planDetailRepository
                .totalAmount(optionalRecruitmentPlan.get().getId());

        if (createDTO.getAmount() > (optionalRecruitmentPlan.get().getAmount() -
                totalAmount)) {
            throw new NotValidException(PlanDetailErrorMessage.NOT_VALID_AMOUNT_EXCEPTION);
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));

        LocalDate dateFormat = LocalDate.parse(date.toString(), format);
        LocalDate periodFrom = LocalDate.parse(createDTO.getPeriodFrom().toString(), format);
        LocalDate periodTo = LocalDate.parse(createDTO.getPeriodTo().toString(), format);

        LocalDate endDateOfRecruitmentPlan = LocalDate.parse(optionalRecruitmentPlan.get().getPeriodTo().toString(),
                format);

        if (periodTo.isAfter(endDateOfRecruitmentPlan)) {
            throw new NotValidException(PlanDetailErrorMessage.NOT_VALID_PERIOD_TO_EXCEPTION);
        }

        PlanDetail planDetail = PlanDetail.builder().amount(createDTO.getAmount())
                .reason(createDTO.getReason())
                .salary(createDTO.getSalary())
                .periodFrom(Date.valueOf(periodFrom))
                .periodTo(Date.valueOf(periodTo))
                .note(createDTO.getNote())
                .date(Date.valueOf(dateFormat))
                .requirement(createDTO.getRequirement())
                .description(createDTO.getDescription())
                .creator(creator)
                .position(optionalPosition.get()).recruitmentPlan(optionalRecruitmentPlan.get())
                .status(PlanDetailStatus.PENDING).build();
        PlanDetail planDetailSaved = planDetailRepository.save(planDetail);
        PlanDetailResponseDTO responseDTO = modelMapper.map(planDetailSaved, PlanDetailResponseDTO.class);
        return responseDTO;
    }

    @Override
    public ResponseWithTotalPage<PlanDetailResponseDTO> getPendingPlanDetails(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<PlanDetail> pageResult = planDetailRepository.findByStatus(PlanDetailStatus.PENDING, pageable);
        List<PlanDetailResponseDTO> list = new ArrayList<PlanDetailResponseDTO>();
        ResponseWithTotalPage<PlanDetailResponseDTO> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (PlanDetail planDetail : pageResult.getContent()) {
                PlanDetailResponseDTO planDetailResponse = modelMapper.map(planDetail, PlanDetailResponseDTO.class);
                list.add(planDetailResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(PlanDetailErrorMessage.LIST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public ResponseWithTotalPage<PlanDetailResponseDTO> getApprovedPlanDetails(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<PlanDetail> pageResult = planDetailRepository.findByStatus(PlanDetailStatus.APPROVED, pageable);
        List<PlanDetailResponseDTO> list = new ArrayList<PlanDetailResponseDTO>();
        ResponseWithTotalPage<PlanDetailResponseDTO> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (PlanDetail planDetail : pageResult.getContent()) {
                PlanDetailResponseDTO planDetailResponse = modelMapper.map(planDetail, PlanDetailResponseDTO.class);
                list.add(planDetailResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(PlanDetailErrorMessage.LIST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public ResponseWithTotalPage<PlanDetailResponseDTO> getCanceledPlanDetails(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<PlanDetail> pageResult = planDetailRepository.findByStatus(PlanDetailStatus.CANCELED, pageable);
        List<PlanDetailResponseDTO> list = new ArrayList<PlanDetailResponseDTO>();
        ResponseWithTotalPage<PlanDetailResponseDTO> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (PlanDetail planDetail : pageResult.getContent()) {
                PlanDetailResponseDTO planDetailResponse = modelMapper.map(planDetail, PlanDetailResponseDTO.class);
                list.add(planDetailResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
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

    @Override
    public ResponseWithTotalPage<PlanDetailResponseDTO> getPlanDetailByApprover(int approverId, int pageNo,
            int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        Employee employee = employeeRepository.findById(approverId)
                .orElseThrow(() -> new NotFoundException(
                        EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));

        Page<PlanDetail> pageResult = planDetailRepository.findByApprover(employee, pageable);
        List<PlanDetailResponseDTO> list = new ArrayList<PlanDetailResponseDTO>();
        ResponseWithTotalPage<PlanDetailResponseDTO> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (PlanDetail planDetail : pageResult.getContent()) {
                PlanDetailResponseDTO planDetailResponse = modelMapper.map(planDetail, PlanDetailResponseDTO.class);
                list.add(planDetailResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(PlanDetailErrorMessage.LIST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public List<IdAndNameResponse> getPlanDetailApprovedByDepartment(int departmentId) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));
        List<PlanDetail> planDetails = planDetailRepository.findApprovedByDepartment(departmentId);

        List<IdAndNameResponse> list = new ArrayList<>();

        for (PlanDetail planDetail : planDetails) {
            IdAndNameResponse response = IdAndNameResponse.builder()
                    .id(planDetail.getId())
                    .name(planDetail.getName())
                    .build();
            list.add(response);
        }
        return list;
    }

    @Override
    public List<AllStatusCounterResponse> getStatusTotal() {
        ArrayList<AllStatusCounterResponse> allList = new ArrayList<>();
        List<Tuple> list = planDetailRepository.getTotalStatusDetail();


        List<CountStatusResponse> responses = new ArrayList<>();
        for (Tuple total: list) {
            CountStatusResponse countStatusResponse = CountStatusResponse.builder()
                    .status(total.get("status").toString())
                    .total(Integer.parseInt(total.get("total").toString()))
                    .build();
            responses.add(countStatusResponse);

        }
        //RecruitmentPlan
        List<CountStatusResponse> countPlan = recruitmentPlanService.getStatusTotal();
        AllStatusCounterResponse allStatusCounterResponsePlan = AllStatusCounterResponse.builder()
                .className("RecruitmentPlan")
                .countStatusResponses(countPlan)
                .build();
        allList.add(allStatusCounterResponsePlan);
        //Plan detail
        AllStatusCounterResponse allStatusCounterResponseDetail = AllStatusCounterResponse.builder()
                .className("PlanDetail")
                .countStatusResponses(responses)
                .build();
        allList.add(allStatusCounterResponseDetail);
        //Recruitment Request
        List<CountStatusResponse> countRequest = recruitmentRequestService.getStatusTotal();
        AllStatusCounterResponse allStatusCounterResponseRequest = AllStatusCounterResponse.builder()
                .className("RecruitmentRequest")
                .countStatusResponses(countRequest)
                .build();
        allList.add(allStatusCounterResponseRequest);
        return allList;
    }
}
