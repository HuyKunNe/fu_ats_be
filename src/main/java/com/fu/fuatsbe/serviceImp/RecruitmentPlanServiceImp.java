package com.fu.fuatsbe.serviceImp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fu.fuatsbe.response.CountStatusResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.RecruimentPlanUpdateDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanActionDTO;
import com.fu.fuatsbe.DTO.RecruitmentPlanCreateDTO;
import com.fu.fuatsbe.constant.department.DepartmentErrorMessage;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.recruitmentPlan.RecruitmentPlanErrorMessage;
import com.fu.fuatsbe.constant.recruitmentPlan.RecruitmentPlanStatus;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.RecruitmentPlan;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.exceptions.NotValidException;
import com.fu.fuatsbe.repository.DepartmentRepository;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.RecruitmentPlanRepository;
import com.fu.fuatsbe.response.IdAndNameResponse;
import com.fu.fuatsbe.response.RecruitmentPlanResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.RecruitmentPlanService;

import lombok.RequiredArgsConstructor;

import javax.persistence.Tuple;

@Service
@RequiredArgsConstructor
public class RecruitmentPlanServiceImp implements RecruitmentPlanService {

    private final RecruitmentPlanRepository recruitmentPlanRepository;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllRecruitmentPlans(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findAll(pageable);
        List<RecruitmentPlanResponse> list = new ArrayList<RecruitmentPlanResponse>();
        ResponseWithTotalPage<RecruitmentPlanResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                list.add(response);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
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
    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllApprovedRecruitmentPlan(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findByStatus(RecruitmentPlanStatus.APPROVED,
                pageable);
        List<RecruitmentPlanResponse> list = new ArrayList<RecruitmentPlanResponse>();
        ResponseWithTotalPage<RecruitmentPlanResponse> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                list.add(response);
            }

            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(RecruitmentPlanErrorMessage.LIST_RECRUITMENTPLAN_EMPTY_EXCEPTION);
        return result;

    }

    @Override
    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllCanceledRecruitmentPlans(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findByStatus(RecruitmentPlanStatus.CANCELED,
                pageable);
        List<RecruitmentPlanResponse> list = new ArrayList<RecruitmentPlanResponse>();
        ResponseWithTotalPage<RecruitmentPlanResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                list.add(response);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(RecruitmentPlanErrorMessage.LIST_RECRUITMENTPLAN_EMPTY_EXCEPTION);
        return result;

    }

    @Override
    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllRejectedRecruitmentPlans(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findByStatus(RecruitmentPlanStatus.REJECTED,
                pageable);
        List<RecruitmentPlanResponse> list = new ArrayList<RecruitmentPlanResponse>();
        ResponseWithTotalPage<RecruitmentPlanResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                list.add(response);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(RecruitmentPlanErrorMessage.LIST_RECRUITMENTPLAN_EMPTY_EXCEPTION);
        return result;

    }

    @Override
    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllPedingRecruitmentPlans(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findByStatus(RecruitmentPlanStatus.PENDING,
                pageable);
        List<RecruitmentPlanResponse> list = new ArrayList<RecruitmentPlanResponse>();
        ResponseWithTotalPage<RecruitmentPlanResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                list.add(response);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(RecruitmentPlanErrorMessage.LIST_RECRUITMENTPLAN_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public RecruitmentPlanResponse updateRecruitmentPlan(int id, RecruimentPlanUpdateDTO updateDTO) {
        RecruitmentPlan recruitmentPlan = recruitmentPlanRepository.findById(id).orElseThrow(
                () -> new NotFoundException(RecruitmentPlanErrorMessage.RECRUITMENTPLAN_NOT_FOUND_EXCEPTION));

        if (recruitmentPlan.getStatus().equalsIgnoreCase(RecruitmentPlanStatus.APPROVED)) {
            throw new NotValidException(RecruitmentPlanErrorMessage.RECRUITMENT_PLAN_IS_APPROVED);
        }

        recruitmentPlan.setStatus(RecruitmentPlanStatus.CANCELED);
        recruitmentPlanRepository.save(recruitmentPlan);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate periodFrom = LocalDate.parse(updateDTO.getPeriodFrom().toString(), format);
        LocalDate periodTo = LocalDate.parse(updateDTO.getPeriodTo().toString(), format);

        RecruitmentPlan recruitmentPlanUpdate = new RecruitmentPlan();

        recruitmentPlanUpdate.setPeriodFrom(Date.valueOf(periodFrom));
        recruitmentPlanUpdate.setPeriodTo(Date.valueOf(periodTo));
        recruitmentPlanUpdate.setAmount(updateDTO.getAmount());
        recruitmentPlanUpdate.setStatus(RecruitmentPlanStatus.PENDING);
        recruitmentPlanUpdate.setCreator(recruitmentPlan.getCreator());
        recruitmentPlanUpdate.setTotalSalary(updateDTO.getTotalSalary());
        recruitmentPlanUpdate.setName(updateDTO.getName());

        RecruitmentPlan recruitmentPlanSaved = recruitmentPlanRepository.save(recruitmentPlanUpdate);

        RecruitmentPlanResponse response = modelMapper.map(recruitmentPlanSaved, RecruitmentPlanResponse.class);

        return response;
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
                    .name(createDTO.getName())
                    .totalSalary(createDTO.getTotalSalary())
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
    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllRecruitmentPlansByApprover(int approverId, int pageNo,
            int pageSize) {

        Employee approver = employeeRepository.findById(approverId)
                .orElseThrow(() -> new NotFoundException(
                        EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findByApprover(approver,
                pageable);
        List<RecruitmentPlanResponse> list = new ArrayList<RecruitmentPlanResponse>();
        ResponseWithTotalPage<RecruitmentPlanResponse> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                list.add(response);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(RecruitmentPlanErrorMessage.LIST_RECRUITMENTPLAN_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllRecruitmentPlansByCreator(int creatorId, int pageNo,
            int pageSize) {

        Employee creator = employeeRepository.findById(creatorId)
                .orElseThrow(() -> new NotFoundException(
                        EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findByCreator(creator,
                pageable);
        List<RecruitmentPlanResponse> list = new ArrayList<RecruitmentPlanResponse>();
        ResponseWithTotalPage<RecruitmentPlanResponse> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                list.add(response);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(RecruitmentPlanErrorMessage.LIST_RECRUITMENTPLAN_EMPTY_EXCEPTION);
        return result;

    }

    @Override
    public ResponseWithTotalPage<RecruitmentPlanResponse> getAllRecruitmentPlansByDepartment(int departmentId,
            int pageNo,
            int pageSize) {

        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<RecruitmentPlan> pageResult = recruitmentPlanRepository.findByDepartmentId(departmentId, pageable);
        List<RecruitmentPlanResponse> list = new ArrayList<RecruitmentPlanResponse>();
        ResponseWithTotalPage<RecruitmentPlanResponse> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (RecruitmentPlan recruitmentPlan : pageResult.getContent()) {
                RecruitmentPlanResponse response = modelMapper.map(recruitmentPlan, RecruitmentPlanResponse.class);
                list.add(response);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(RecruitmentPlanErrorMessage.LIST_RECRUITMENTPLAN_EMPTY_EXCEPTION);
        return result;

    }

    @Override
    public List<IdAndNameResponse> getApprovedByDepartment(int departmentId) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));
        List<RecruitmentPlan> recruitmentPlans = recruitmentPlanRepository.findApprovedByDepartment(departmentId);
        List<IdAndNameResponse> list = new ArrayList<>();

        for (RecruitmentPlan recruitmentPlan : recruitmentPlans) {
            IdAndNameResponse response = IdAndNameResponse.builder()
                    .id(recruitmentPlan.getId())
                    .name(recruitmentPlan.getName())
                    .build();
            list.add(response);
        }
        return list;
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

    @Override
    public CountStatusResponse getStatusTotal() {
        List<Tuple> list = recruitmentPlanRepository.getTotalStatus();

        List<String> statusList = new ArrayList<>();
        List<Integer> totalList = new ArrayList<>();
        for (Tuple total : list) {
            statusList.add(total.get("status").toString());
            totalList.add(Integer.parseInt(total.get("total").toString()));

        }
        CountStatusResponse countStatusResponse = CountStatusResponse.builder()
                .status(statusList)
                .total(totalList)
                .build();
        return countStatusResponse;
    }

    @Override
    public int getSalaryFund(int recruitmentRequestPlanId) {
        RecruitmentPlan recruitmentPlan = recruitmentPlanRepository.findById(recruitmentRequestPlanId)
                .orElseThrow(() -> new NotFoundException(
                        RecruitmentPlanErrorMessage.RECRUITMENTPLAN_NOT_FOUND_EXCEPTION));

        int result = 0;
        int salary = Integer.parseInt(recruitmentPlan.getTotalSalary().replaceAll("\\D+", ""));
        result = salary - recruitmentPlanRepository.totalSalaryFund(recruitmentRequestPlanId);

        return result;
    }

    @Override
    public List<String> getYearFromPlan() {
        List<String> result = new ArrayList<String>();

        result = recruitmentPlanRepository.getYearFromPlan();
        return result;
    }
}
