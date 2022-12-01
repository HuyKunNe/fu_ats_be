package com.fu.fuatsbe.serviceImp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fu.fuatsbe.DTO.*;
import com.fu.fuatsbe.constant.department.DepartmentErrorMessage;
import com.fu.fuatsbe.repository.*;
import com.fu.fuatsbe.response.CountStatusResponse;
import com.fu.fuatsbe.response.IdAndNameResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.constant.city.CityErrorMessage;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.planDetail.PlanDetailErrorMessage;
import com.fu.fuatsbe.constant.planDetail.PlanDetailStatus;
import com.fu.fuatsbe.constant.postion.PositionErrorMessage;
import com.fu.fuatsbe.constant.recruitmentRequest.RecruitmentRequestErrorMessage;
import com.fu.fuatsbe.constant.recruitmentRequest.RecruitmentRequestStatus;
import com.fu.fuatsbe.entity.City;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.PlanDetail;
import com.fu.fuatsbe.entity.Position;
import com.fu.fuatsbe.entity.RecruitmentRequest;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.exceptions.NotValidException;
import com.fu.fuatsbe.response.RecruitmentRequestResponse;
import com.fu.fuatsbe.response.RecruitmentRequestResponseWithJobApply;
import com.fu.fuatsbe.service.RecruitmentRequestService;

import lombok.RequiredArgsConstructor;

import javax.persistence.Tuple;

@Service
@RequiredArgsConstructor
public class RecruitmentRequestServiceImp implements RecruitmentRequestService {

    @Autowired
    private final RecruitmentRequestRepository recruitmentRequestRepository;

    private static final String THOA_THUAN = "Negotiable";

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final PlanDetailRepository planDetailRepository;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final CityRepository cityRepository;
    private final JobApplyRepository jobApplyRepository;

    @Override
    public ResponseWithTotalPage<RecruitmentRequestResponse> getAllRecruitmentRequests(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<RecruitmentRequest> pageResult = recruitmentRequestRepository.findAll(pageable);
        List<RecruitmentRequestResponse> list = new ArrayList<RecruitmentRequestResponse>();
        ResponseWithTotalPage<RecruitmentRequestResponse> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (RecruitmentRequest recruitmentRequest : pageResult.getContent()) {
                RecruitmentRequestResponse response = modelMapper.map(recruitmentRequest,
                        RecruitmentRequestResponse.class);
                if (recruitmentRequest.getSalaryTo() != null && recruitmentRequest.getSalaryFrom() != null) {
                    response.setSalaryDetail(
                            (recruitmentRequest.getSalaryFrom().replaceAll("VNĐ", "").trim() + " - "
                                    + recruitmentRequest.getSalaryTo())
                                    .trim());
                } else if (recruitmentRequest.getSalaryFrom() == null
                        && !recruitmentRequest.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Lên đến " + recruitmentRequest.getSalaryTo());
                } else if (recruitmentRequest.getSalaryTo() == null
                        && !recruitmentRequest.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Trên " + recruitmentRequest.getSalaryFrom());
                } else
                    response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
                list.add(response);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(RecruitmentRequestErrorMessage.LIST_RECRUITMENT_REQUEST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public RecruitmentRequestResponse getRecruitmentRequestById(int id) {
        RecruitmentRequest recruitmentRequest = recruitmentRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));
        RecruitmentRequestResponse response = modelMapper.map(recruitmentRequest, RecruitmentRequestResponse.class);
        if (recruitmentRequest.getSalaryTo() != null && recruitmentRequest.getSalaryFrom() != null) {
            response.setSalaryDetail(
                    (recruitmentRequest.getSalaryFrom().replaceAll("VNĐ", "").trim() + " - "
                            + recruitmentRequest.getSalaryTo())
                            .trim());
        } else if (recruitmentRequest.getSalaryFrom() == null
                && !recruitmentRequest.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
            response.setSalaryDetail("Lên đến " + recruitmentRequest.getSalaryTo());
        } else if (recruitmentRequest.getSalaryTo() == null
                && !recruitmentRequest.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)) {
            response.setSalaryDetail("Trên " + recruitmentRequest.getSalaryFrom());
        } else
            response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
        return response;
    }

    @Override
    public ResponseWithTotalPage<RecruitmentRequestResponseWithJobApply> getAllOpenRecruitmentRequest(int pageNo,
            int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<RecruitmentRequest> pageResult = recruitmentRequestRepository
                .findByStatus(RecruitmentRequestStatus.OPENING, pageable);

        List<RecruitmentRequestResponseWithJobApply> list = new ArrayList<RecruitmentRequestResponseWithJobApply>();
        ResponseWithTotalPage<RecruitmentRequestResponseWithJobApply> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (RecruitmentRequest recruitmentRequest : pageResult.getContent()) {
                RecruitmentRequestResponseWithJobApply response = modelMapper.map(recruitmentRequest,
                        RecruitmentRequestResponseWithJobApply.class);
                if (recruitmentRequest.getSalaryTo() != null && recruitmentRequest.getSalaryFrom() != null) {
                    response.setSalaryDetail(
                            (recruitmentRequest.getSalaryFrom().replaceAll("VNĐ", "").trim() + " - "
                                    + recruitmentRequest.getSalaryTo())
                                    .trim());
                } else if (recruitmentRequest.getSalaryFrom() == null
                        && !recruitmentRequest.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Lên đến " + recruitmentRequest.getSalaryTo());
                } else if (recruitmentRequest.getSalaryTo() == null
                        && !recruitmentRequest.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Trên " + recruitmentRequest.getSalaryFrom());
                } else
                    response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
                int total = jobApplyRepository.countByRecruitmentRequestId(recruitmentRequest.getId());
                response.setTotalJobApply(total);
                list.add(response);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(RecruitmentRequestErrorMessage.LIST_RECRUITMENT_REQUEST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public ResponseWithTotalPage<RecruitmentRequestResponse> getAllFilledRecruitmentRequest(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<RecruitmentRequest> pageResult = recruitmentRequestRepository
                .findByStatus(RecruitmentRequestStatus.FILLED, pageable);
        List<RecruitmentRequestResponse> list = new ArrayList<RecruitmentRequestResponse>();
        ResponseWithTotalPage<RecruitmentRequestResponse> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (RecruitmentRequest recruitmentRequest : pageResult.getContent()) {
                RecruitmentRequestResponse response = modelMapper.map(recruitmentRequest,
                        RecruitmentRequestResponse.class);
                if (recruitmentRequest.getSalaryTo() != null && recruitmentRequest.getSalaryFrom() != null) {
                    response.setSalaryDetail(
                            (recruitmentRequest.getSalaryFrom().replaceAll("VNĐ", "").trim() + " - "
                                    + recruitmentRequest.getSalaryTo())
                                    .trim());
                } else if (recruitmentRequest.getSalaryFrom() == null
                        && !recruitmentRequest.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Lên đến " + recruitmentRequest.getSalaryTo());
                } else if (recruitmentRequest.getSalaryTo() == null
                        && !recruitmentRequest.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Trên " + recruitmentRequest.getSalaryFrom());
                } else
                    response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
                list.add(response);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(RecruitmentRequestErrorMessage.LIST_RECRUITMENT_REQUEST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public ResponseWithTotalPage<RecruitmentRequestResponse> getAllClosedRecruitmentRequest(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<RecruitmentRequest> pageResult = recruitmentRequestRepository
                .findByStatus(RecruitmentRequestStatus.CLOSED, pageable);
        List<RecruitmentRequestResponse> list = new ArrayList<RecruitmentRequestResponse>();
        ResponseWithTotalPage<RecruitmentRequestResponse> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (RecruitmentRequest recruitmentRequest : pageResult.getContent()) {
                RecruitmentRequestResponse response = modelMapper.map(recruitmentRequest,
                        RecruitmentRequestResponse.class);
                if (recruitmentRequest.getSalaryTo() != null && recruitmentRequest.getSalaryFrom() != null) {
                    response.setSalaryDetail(
                            (recruitmentRequest.getSalaryFrom().replaceAll("VNĐ", "").trim() + " - "
                                    + recruitmentRequest.getSalaryTo())
                                    .trim());
                } else if (recruitmentRequest.getSalaryFrom() == null
                        && !recruitmentRequest.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Lên đến " + recruitmentRequest.getSalaryTo());
                } else if (recruitmentRequest.getSalaryTo() == null
                        && !recruitmentRequest.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Trên " + recruitmentRequest.getSalaryFrom());
                } else
                    response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
                list.add(response);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(RecruitmentRequestErrorMessage.LIST_RECRUITMENT_REQUEST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public RecruitmentRequestResponse updateRecruitmentRequest(int id, RecruitmentRequestUpdateDTO updateDTO) {
        RecruitmentRequest recruitmentRequest = recruitmentRequestRepository.findById(id).orElseThrow(
                () -> new NotFoundException(RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        City city = cityRepository.findByName(updateDTO.getCityName())
                .orElseThrow(() -> new NotFoundException(CityErrorMessage.NOT_FOUND));

        LocalDate expiryDate = LocalDate.parse(updateDTO.getExpiryDate().toString(), format);

        LocalDate periodToPlanDetail = LocalDate.parse(recruitmentRequest.getPlanDetail().getPeriodTo().toString(),
                format);

        if (expiryDate.isAfter(periodToPlanDetail)) {
            throw new NotValidException(RecruitmentRequestErrorMessage.NOT_VALID_EXPIRY_DATE_EXCEPTION);
        }

        if (updateDTO.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)
                && updateDTO.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
            updateDTO.setSalaryTo(null);
        } else if (updateDTO.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)) {
            updateDTO.setSalaryFrom(null);
        } else if (updateDTO.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
            updateDTO.setSalaryTo(null);
        }

        recruitmentRequest.setAmount(updateDTO.getAmount());
        recruitmentRequest.setExpiryDate(updateDTO.getExpiryDate());
        recruitmentRequest.setIndustry(updateDTO.getIndustry());
        recruitmentRequest.setJobLevel(updateDTO.getJobLevel());
        recruitmentRequest.setExperience(updateDTO.getExperience());
        recruitmentRequest.setSalaryFrom(updateDTO.getSalaryFrom());
        recruitmentRequest.setSalaryTo(updateDTO.getSalaryTo());
        recruitmentRequest.setName(updateDTO.getName());
        recruitmentRequest.setEducationLevel(updateDTO.getEducationLevel());
        recruitmentRequest.setAddress(updateDTO.getAddress());
        recruitmentRequest.setForeignLanguage(updateDTO.getForeignLanguage());
        recruitmentRequest.setCities(city);
        recruitmentRequest.setTypeOfWork(updateDTO.getTypeOfWork());
        recruitmentRequest.setDescription(updateDTO.getDescription());
        recruitmentRequest.setPosition(position);

        RecruitmentRequest recruitmentRequestSaved = recruitmentRequestRepository.save(recruitmentRequest);
        RecruitmentRequestResponse response = modelMapper.map(recruitmentRequestSaved,
                RecruitmentRequestResponse.class);
        if (recruitmentRequest.getSalaryTo() != null && recruitmentRequest.getSalaryFrom() != null) {
            response.setSalaryDetail(
                    (recruitmentRequest.getSalaryFrom().replaceAll("VNĐ", "").trim() + " - "
                            + recruitmentRequest.getSalaryTo())
                            .trim());
        } else if (recruitmentRequest.getSalaryFrom() == null
                && !recruitmentRequest.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
            response.setSalaryDetail("Lên đến " + recruitmentRequest.getSalaryTo());
        } else if (recruitmentRequest.getSalaryTo() == null
                && !recruitmentRequest.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)) {
            response.setSalaryDetail("Trên " + recruitmentRequest.getSalaryFrom());
        } else
            response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
        return response;

    }

    @Override
    public RecruitmentRequestResponse createRecruitmentRequest(RecruitmentRequestCreateDTO createDTO) {
        Optional<Employee> optionalCreator = employeeRepository.findById(createDTO.getEmployeeId());
        PlanDetail planDetail = planDetailRepository.findById(createDTO.getPlanDetailId())
                .orElseThrow(() -> new NotFoundException(PlanDetailErrorMessage.PLAN_DETAIL_NOT_FOUND_EXCEPTION));
        Position position = positionRepository.findPositionByName(createDTO.getPositionName())
                .orElseThrow(() -> new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST));
        if (!planDetail.getStatus().equalsIgnoreCase(PlanDetailStatus.APPROVED))
            throw new NotValidException(PlanDetailErrorMessage.PLAN_DETAIL_NOT_APPROVED_EXCEPTION);
        if (optionalCreator.isPresent()) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            LocalDate dateFormat = LocalDate.parse(date.toString(), format);
            LocalDate expiryDate = LocalDate.parse(createDTO.getExpiryDate().toString(), format);

            LocalDate periodToPlanDetail = LocalDate.parse(planDetail.getPeriodTo().toString(), format);

            if (expiryDate.isAfter(periodToPlanDetail)) {
                throw new NotValidException(RecruitmentRequestErrorMessage.NOT_VALID_EXPIRY_DATE_EXCEPTION);
            }

            City city = cityRepository.findByName(createDTO.getCityName())
                    .orElseThrow(() -> new NotFoundException(CityErrorMessage.NOT_FOUND));

            if (createDTO.getAmount() != planDetail.getAmount()) {
                throw new NotValidException(RecruitmentRequestErrorMessage.NOT_VALID_AMOUNT_EXCEPTION);
            }

            if (createDTO.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)
                    && createDTO.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
                createDTO.setSalaryTo(null);
            } else if (createDTO.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)) {
                createDTO.setSalaryFrom(null);
            } else if (createDTO.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
                createDTO.setSalaryTo(null);
            }

            RecruitmentRequest request = RecruitmentRequest.builder().date(Date.valueOf(dateFormat))
                    .expiryDate(Date.valueOf(expiryDate)).industry(createDTO.getIndustry())
                    .amount(createDTO.getAmount()).jobLevel(createDTO.getJobLevel())
                    .status(RecruitmentRequestStatus.OPENING).experience(createDTO.getExperience())
                    .cities(city)
                    .typeOfWork(createDTO.getTypeOfWork())
                    .benefit(createDTO.getBenefit())
                    .name(createDTO.getName())
                    .foreignLanguage(createDTO.getForeignLanguage())
                    .educationLevel(createDTO.getEducationLevel())
                    .requirement(createDTO.getRequirement())
                    .salaryFrom(createDTO.getSalaryFrom()).salaryTo(createDTO.getSalaryTo())
                    .description(createDTO.getDescription()).creator(optionalCreator.get()).planDetail(planDetail)
                    .address(createDTO.getAddress())
                    .position(position).build();
            recruitmentRequestRepository.save(request);
            RecruitmentRequestResponse response = modelMapper.map(request, RecruitmentRequestResponse.class);
            if (request.getSalaryTo() != null && request.getSalaryFrom() != null) {
                response.setSalaryDetail(
                        (request.getSalaryFrom().replaceAll("VNĐ", "").trim() + " - " + request.getSalaryTo())
                                .trim());
            } else if (request.getSalaryFrom() == null
                    && !request.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
                response.setSalaryDetail("Lên đến " + request.getSalaryTo());
            } else if (request.getSalaryTo() == null
                    && !request.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)) {
                response.setSalaryDetail("Trên " + request.getSalaryFrom());
            } else
                response.setSalaryDetail(request.getSalaryFrom());
            return response;
        } else {
            throw new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    public RecruitmentRequestResponse closeRecruitmentRequest(int id) {
        RecruitmentRequest recruitmentRequest = recruitmentRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));
        recruitmentRequest.setStatus(RecruitmentRequestStatus.CLOSED);
        RecruitmentRequest recruitmentRequestSaved = recruitmentRequestRepository.save(recruitmentRequest);
        RecruitmentRequestResponse response = modelMapper.map(recruitmentRequestSaved,
                RecruitmentRequestResponse.class);
        if (recruitmentRequest.getSalaryTo() != null && recruitmentRequest.getSalaryFrom() != null) {
            response.setSalaryDetail(
                    (recruitmentRequest.getSalaryFrom().replaceAll("VNĐ", "").trim() + " - "
                            + recruitmentRequest.getSalaryTo())
                            .trim());
        } else if (recruitmentRequest.getSalaryFrom() == null
                && !recruitmentRequest.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
            response.setSalaryDetail("Lên đến " + recruitmentRequest.getSalaryTo());
        } else if (recruitmentRequest.getSalaryTo() == null
                && !recruitmentRequest.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)) {
            response.setSalaryDetail("Trên " + recruitmentRequest.getSalaryFrom());
        } else
            response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
        return response;
    }

    @Override
    public ResponseWithTotalPage<RecruitmentRequestResponse> getAllRecruitmentRequestByCreator(int id, int pageNo,
            int pageSize) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<RecruitmentRequest> pageResult = recruitmentRequestRepository.findByCreator(employee, pageable);
        List<RecruitmentRequestResponse> list = new ArrayList<RecruitmentRequestResponse>();
        ResponseWithTotalPage<RecruitmentRequestResponse> result = new ResponseWithTotalPage<>();

        if (pageResult.hasContent()) {
            for (RecruitmentRequest recruitmentRequest : pageResult.getContent()) {
                RecruitmentRequestResponse response = modelMapper.map(recruitmentRequest,
                        RecruitmentRequestResponse.class);
                if (recruitmentRequest.getSalaryTo() != null && recruitmentRequest.getSalaryFrom() != null) {
                    response.setSalaryDetail(
                            (recruitmentRequest.getSalaryFrom().replaceAll("VNĐ", "").trim() + " - "
                                    + recruitmentRequest.getSalaryTo())
                                    .trim());
                } else if (recruitmentRequest.getSalaryFrom() == null
                        && !recruitmentRequest.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Lên đến " + recruitmentRequest.getSalaryTo());
                } else if (recruitmentRequest.getSalaryTo() == null
                        && !recruitmentRequest.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Trên " + recruitmentRequest.getSalaryFrom());
                } else
                    response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
                list.add(response);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(RecruitmentRequestErrorMessage.LIST_RECRUITMENT_REQUEST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public List<RecruitmentRequestResponse> searchRecruitmentRequest(RecruitmentRequestSearchDTO searchDTO) {
        List<RecruitmentRequestResponse> result = new ArrayList<>();
        List<RecruitmentRequest> list = recruitmentRequestRepository.searchRecruitmentRequest(searchDTO.getJobName(),
                searchDTO.getCity(), searchDTO.getIndustry(), searchDTO.getJobLevel(), searchDTO.getTypeOfWork(),
                searchDTO.getSalaryFrom(),
                searchDTO.getSalaryTo(), searchDTO.getExperience());
        if (!list.isEmpty()) {
            for (RecruitmentRequest recruitmentRequest : list) {
                RecruitmentRequestResponse response = modelMapper.map(recruitmentRequest,
                        RecruitmentRequestResponse.class);
                if (recruitmentRequest.getSalaryTo() != null && recruitmentRequest.getSalaryFrom() != null) {
                    response.setSalaryDetail(
                            (recruitmentRequest.getSalaryFrom().replaceAll("VNĐ", "").trim() + " - "
                                    + recruitmentRequest.getSalaryTo())
                                    .trim());
                } else if (recruitmentRequest.getSalaryFrom() == null
                        && !recruitmentRequest.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Lên đến " + recruitmentRequest.getSalaryTo());
                } else if (recruitmentRequest.getSalaryTo() == null
                        && !recruitmentRequest.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Trên " + recruitmentRequest.getSalaryFrom());
                } else
                    response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
                result.add(response);
            }
        }
        return result;
    }

    @Override
    public RecruitmentSearchCategoryDTO searchCategory() {
        RecruitmentSearchCategoryDTO recruitmentSearchCategoryDTO = RecruitmentSearchCategoryDTO.builder()
                .jobTitle(recruitmentRequestRepository.getDistinctByPosition())
                .position(positionRepository.getAllPositionName())
                .industry(recruitmentRequestRepository.getDistinctByIndustry())
                .province(cityRepository.getAllCityName())
                .build();
        return recruitmentSearchCategoryDTO;
    }

    @Override
    public List<RecruitmentRequestResponse> getNewestRecruitmentRequest() {
        Pageable pageable = PageRequest.of(0, 4);
        Page<RecruitmentRequest> pageResult = recruitmentRequestRepository.findByOrderByIdDesc(pageable);
        List<RecruitmentRequestResponse> list = new ArrayList<RecruitmentRequestResponse>();
        if (pageResult.hasContent()) {
            for (RecruitmentRequest recruitmentRequest : pageResult.getContent()) {
                RecruitmentRequestResponse response = modelMapper.map(recruitmentRequest,
                        RecruitmentRequestResponse.class);
                if (recruitmentRequest.getSalaryTo() != null && recruitmentRequest.getSalaryFrom() != null) {
                    response.setSalaryDetail(
                            (recruitmentRequest.getSalaryFrom().replaceAll("VNĐ", "").trim() + " - "
                                    + recruitmentRequest.getSalaryTo())
                                    .trim());
                } else if (recruitmentRequest.getSalaryFrom() == null
                        && !recruitmentRequest.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Lên đến " + recruitmentRequest.getSalaryTo());
                } else if (recruitmentRequest.getSalaryTo() == null
                        && !recruitmentRequest.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Trên " + recruitmentRequest.getSalaryFrom());
                } else
                    response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
                list.add(response);
            }
        }
        return list;
    }

    @Override
    public CountStatusResponse getStatusTotal() {
        List<Tuple> list = recruitmentRequestRepository.getTotalStatusRequest();

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
    public List<IdAndNameResponse> getIdAndNameRequestByDepartment(int departmentId) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));
        List<Tuple> listRepo = recruitmentRequestRepository.getIdAndNameRequestByDepartment(departmentId);
        List<IdAndNameResponse> responses = new ArrayList<>();
        for (Tuple tuple : listRepo) {
            IdAndNameResponse idAndNameResponse = IdAndNameResponse.builder()
                    .id(Integer.parseInt(tuple.get("id").toString()))
                    .name(tuple.get("name").toString())
                    .build();
            responses.add(idAndNameResponse);
        }
        return responses;
    }

    @Override
    public List<IdAndNameResponse> getAllActiveRequest() {
        List<Tuple> request = recruitmentRequestRepository.getAllActiveRequest();
        List<IdAndNameResponse> responses = new ArrayList<>();
        for (Tuple tuple : request) {
            IdAndNameResponse idAndNameResponse = IdAndNameResponse.builder()
                    .id(Integer.parseInt(tuple.get("id").toString()))
                    .name(tuple.get("name").toString())
                    .build();
            responses.add(idAndNameResponse);
        }
        return responses;
    }

    @Override
    public List<RecruitmentRequestResponse> getExpiryDateRecruitmentRequest() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        String date = localDate.format(format);
        Date currentDate = Date.valueOf(date);

        List<RecruitmentRequest> pageResult = recruitmentRequestRepository
                .findByExpiryDateLessThanAndStatusNotLikeOrderByExpiryDateDesc(
                        currentDate, RecruitmentRequestStatus.CLOSED);
        List<RecruitmentRequestResponse> list = new ArrayList<RecruitmentRequestResponse>();
        if (pageResult.size() > 0) {
            for (RecruitmentRequest recruitmentRequest : pageResult) {
                RecruitmentRequestResponse response = modelMapper.map(recruitmentRequest,
                        RecruitmentRequestResponse.class);
                if (recruitmentRequest.getSalaryTo() != null && recruitmentRequest.getSalaryFrom() != null) {
                    response.setSalaryDetail(
                            (recruitmentRequest.getSalaryFrom().replaceAll("VNĐ", "").trim() + " - "
                                    + recruitmentRequest.getSalaryTo())
                                    .trim());
                } else if (recruitmentRequest.getSalaryFrom() == null
                        && !recruitmentRequest.getSalaryTo().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Lên đến " + recruitmentRequest.getSalaryTo());
                } else if (recruitmentRequest.getSalaryTo() == null
                        && !recruitmentRequest.getSalaryFrom().equalsIgnoreCase(THOA_THUAN)) {
                    response.setSalaryDetail("Trên " + recruitmentRequest.getSalaryFrom());
                } else
                    response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
                list.add(response);
            }
        }
        return list;
    }

    @Override
    public boolean closeListRecruitmentRequest(ListRequestClose listId) {

        for (Integer id : listId.getListId()) {
            RecruitmentRequest recruitmentRequest = recruitmentRequestRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(
                            RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));

            recruitmentRequest.setStatus(RecruitmentRequestStatus.CLOSED);
            recruitmentRequestRepository.save(recruitmentRequest);
        }
        return true;

    }

}
