package com.fu.fuatsbe.serviceImp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fu.fuatsbe.DTO.*;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.fu.fuatsbe.repository.CityRepository;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.PlanDetailRepository;
import com.fu.fuatsbe.repository.PositionRepository;
import com.fu.fuatsbe.repository.RecruitmentRequestRepository;
import com.fu.fuatsbe.response.RecruitmentRequestResponse;
import com.fu.fuatsbe.service.RecruitmentRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentRequestServiceImp implements RecruitmentRequestService {

    @Autowired
    private final RecruitmentRequestRepository recruitmentRequestRepository;

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final PlanDetailRepository planDetailRepository;
    private final PositionRepository positionRepository;
    private final CityRepository cityRepository;

    @Override
    public ResponseWithTotalPage getAllRecruitmentRequests(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<RecruitmentRequest> pageResult = recruitmentRequestRepository.findAll(pageable);
        List<Object> result = new ArrayList<>();
        ResponseWithTotalPage responseWithTotalPages = null;

        if (pageResult.hasContent()) {
            for (RecruitmentRequest recruitmentRequest : pageResult.getContent()) {
                RecruitmentRequestResponse response = modelMapper.map(recruitmentRequest,
                        RecruitmentRequestResponse.class);
                if (recruitmentRequest.getSalaryTo() != null) {
                    response.setSalaryDetail(
                            (recruitmentRequest.getSalaryFrom() + " - " + recruitmentRequest.getSalaryTo())
                                    .trim());
                } else {
                    response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
                }
                result.add(response);
                responseWithTotalPages = ResponseWithTotalPage
                        .builder()
                        .totalPage(pageResult.getTotalPages())
                        .responseList(result)
                        .build();
            }
        } else
            throw new ListEmptyException(RecruitmentRequestErrorMessage.LIST_RECRUITMENT_REQUEST_EMPTY_EXCEPTION);
        return responseWithTotalPages;
    }

    @Override
    public RecruitmentRequestResponse getRecruitmentRequestById(int id) {
        RecruitmentRequest recruitmentRequest = recruitmentRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));
        RecruitmentRequestResponse response = modelMapper.map(recruitmentRequest, RecruitmentRequestResponse.class);
        if (recruitmentRequest.getSalaryTo() != null) {
            response.setSalaryDetail(
                    (recruitmentRequest.getSalaryFrom() + " - " + recruitmentRequest.getSalaryTo())
                            .trim());
        } else {
            response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
        }
        return response;
    }

    @Override
    public ResponseWithTotalPage getAllOpenRecruitmentRequest(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<RecruitmentRequest> pageResult = recruitmentRequestRepository
                .findByStatus(RecruitmentRequestStatus.OPENING, pageable);
        List<Object> result = new ArrayList<>();
        ResponseWithTotalPage responseWithTotalPages = null;
        if (pageResult.hasContent()) {
            for (RecruitmentRequest recruitmentRequest : pageResult.getContent()) {
                RecruitmentRequestResponse response = modelMapper.map(recruitmentRequest,
                        RecruitmentRequestResponse.class);
                if (recruitmentRequest.getSalaryTo() != null) {
                    response.setSalaryDetail(
                            (recruitmentRequest.getSalaryFrom() + " - " + recruitmentRequest.getSalaryTo())
                                    .trim());
                } else {
                    response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
                }
                result.add(response);
                responseWithTotalPages = ResponseWithTotalPage.builder()
                        .totalPage(pageResult.getTotalPages())
                        .responseList(result)
                        .build();
            }
        } else
            throw new ListEmptyException(RecruitmentRequestErrorMessage.LIST_RECRUITMENT_REQUEST_EMPTY_EXCEPTION);
        return responseWithTotalPages;
    }

    @Override
    public ResponseWithTotalPage getAllFilledRecruitmentRequest(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<RecruitmentRequest> pageResult = recruitmentRequestRepository
                .findByStatus(RecruitmentRequestStatus.FILLED, pageable);
        List<Object> result = new ArrayList<>();
        ResponseWithTotalPage responseWithTotalPages = null;

        if (pageResult.hasContent()) {
            for (RecruitmentRequest recruitmentRequest : pageResult.getContent()) {
                RecruitmentRequestResponse response = modelMapper.map(recruitmentRequest,
                        RecruitmentRequestResponse.class);
                if (recruitmentRequest.getSalaryTo() != null) {
                    response.setSalaryDetail(
                            (recruitmentRequest.getSalaryFrom() + " - " + recruitmentRequest.getSalaryTo())
                                    .trim());
                } else {
                    response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
                }
                result.add(response);
                responseWithTotalPages = ResponseWithTotalPage
                        .builder()
                        .totalPage(pageResult.getTotalPages())
                        .responseList(result)
                        .build();
            }
        } else
            throw new ListEmptyException(RecruitmentRequestErrorMessage.LIST_RECRUITMENT_REQUEST_EMPTY_EXCEPTION);
        return responseWithTotalPages;
    }

    @Override
    public ResponseWithTotalPage getAllClosedRecruitmentRequest(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<RecruitmentRequest> pageResult = recruitmentRequestRepository
                .findByStatus(RecruitmentRequestStatus.CLOSED, pageable);
        List<Object> result = new ArrayList<>();
        ResponseWithTotalPage responseWithTotalPages = null;

        if (pageResult.hasContent()) {
            for (RecruitmentRequest recruitmentRequest : pageResult.getContent()) {
                RecruitmentRequestResponse response = modelMapper.map(recruitmentRequest,
                        RecruitmentRequestResponse.class);
                if (recruitmentRequest.getSalaryTo() != null) {
                    response.setSalaryDetail(
                            (recruitmentRequest.getSalaryFrom() + " - " + recruitmentRequest.getSalaryTo())
                                    .trim());
                } else {
                    response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
                }
                result.add(response);
            }
            responseWithTotalPages = ResponseWithTotalPage
                    .builder()
                    .responseList(result)
                    .totalPage(pageResult.getTotalPages())
                    .build();
        } else
            throw new ListEmptyException(RecruitmentRequestErrorMessage.LIST_RECRUITMENT_REQUEST_EMPTY_EXCEPTION);
        return responseWithTotalPages;
    }

    @Override
    public RecruitmentRequestResponse updateRecruitmentRequest(int id, RecruitmentRequestUpdateDTO updateDTO) {
        RecruitmentRequest recruitmentRequest = recruitmentRequestRepository.findById(id).orElseThrow(
                () -> new NotFoundException(RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST));

        List<City> cities = new ArrayList<>();
        for (String cityName : updateDTO.getCityName()) {
            City city = cityRepository.findByName(cityName)
                    .orElseThrow(() -> new NotFoundException(CityErrorMessage.NOT_FOUND));

            cities.add(city);
        }

        recruitmentRequest.setAmount(updateDTO.getAmount());
        recruitmentRequest.setExpiryDate(updateDTO.getExpiryDate());
        recruitmentRequest.setIndustry(updateDTO.getIndustry());
        recruitmentRequest.setJobLevel(updateDTO.getJobLevel());
        recruitmentRequest.setExperience(updateDTO.getExperience());
        recruitmentRequest.setSalaryFrom(updateDTO.getSalaryFrom());
        recruitmentRequest.setSalaryTo(updateDTO.getSalaryTo());
        recruitmentRequest.setEducationLevel(updateDTO.getEducationLevel());
        recruitmentRequest.setAddress(updateDTO.getAddress());
        recruitmentRequest.setForeignLanguage(updateDTO.getForeignLanguage());
        recruitmentRequest.setCities(cities);
        recruitmentRequest.setTypeOfWork(updateDTO.getTypeOfWork());
        recruitmentRequest.setDescription(updateDTO.getDescription());
        recruitmentRequest.setPosition(position);

        RecruitmentRequest recruitmentRequestSaved = recruitmentRequestRepository.save(recruitmentRequest);
        RecruitmentRequestResponse response = modelMapper.map(recruitmentRequestSaved,
                RecruitmentRequestResponse.class);
        if (recruitmentRequestSaved.getSalaryTo() != null) {
            response.setSalaryDetail(
                    (recruitmentRequestSaved.getSalaryFrom() + " - " + recruitmentRequestSaved.getSalaryTo())
                            .trim());
        } else {
            response.setSalaryDetail(recruitmentRequestSaved.getSalaryFrom());
        }
        return response;

    }

    @Override
    public RecruitmentRequestResponse createRecruitmentRequest(RecruitmentRequestCreateDTO createDTO) {
        Optional<Employee> optionalCreator = employeeRepository.findById(createDTO.getEmployeeId());
        PlanDetail planDetail = planDetailRepository.findById(createDTO.getPlanDetailId())
                .orElseThrow(() -> new NotFoundException(PlanDetailErrorMessage.PLAN_DETAIL_NOT_FOUND_EXCEPTION));
        Position position = positionRepository.findById(createDTO.getPositionId())
                .orElseThrow(() -> new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST));
        if (!planDetail.getStatus().equalsIgnoreCase(PlanDetailStatus.APPROVED))
            throw new NotValidException(PlanDetailErrorMessage.PLAN_DETAIL_NOT_APPROVED_EXCEPTION);
        if (optionalCreator.isPresent()) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            LocalDate dateFormat = LocalDate.parse(date.toString(), format);
            LocalDate expiryDate = LocalDate.parse(createDTO.getExpiryDate().toString(), format);

            List<City> cities = new ArrayList<>();
            for (String cityName : createDTO.getCityName()) {
                City city = cityRepository.findByName(cityName)
                        .orElseThrow(() -> new NotFoundException(CityErrorMessage.NOT_FOUND));

                cities.add(city);
            }

            RecruitmentRequest request = RecruitmentRequest.builder().date(Date.valueOf(dateFormat))
                    .expiryDate(Date.valueOf(expiryDate)).industry(createDTO.getIndustry())
                    .amount(createDTO.getAmount()).jobLevel(createDTO.getJobLevel())
                    .status(RecruitmentRequestStatus.OPENING).experience(createDTO.getExperience())
                    .cities(cities)
                    .typeOfWork(createDTO.getTypeOfWork())
                    .benefit(createDTO.getBenefit())
                    .foreignLanguage(createDTO.getForeignLanguage())
                    .educationLevel(createDTO.getEducationLevel())
                    .requirement(createDTO.getRequirement())
                    .salaryFrom(createDTO.getSalaryFrom()).salaryTo(createDTO.getSalaryTo())
                    .description(createDTO.getDescription()).creator(optionalCreator.get()).planDetail(planDetail)
                    .address(createDTO.getAddress())
                    .position(position).build();
            recruitmentRequestRepository.save(request);
            RecruitmentRequestResponse response = modelMapper.map(request, RecruitmentRequestResponse.class);
            if (request.getSalaryTo() != null) {
                response.setSalaryDetail((request.getSalaryFrom() + " - " + request.getSalaryTo()).trim());
            } else {
                response.setSalaryDetail(request.getSalaryFrom());
            }
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
        if (recruitmentRequestSaved.getSalaryTo() != null) {
            response.setSalaryDetail(
                    (recruitmentRequestSaved.getSalaryFrom() + " - " + recruitmentRequestSaved.getSalaryTo()).trim());
        } else {
            response.setSalaryDetail(recruitmentRequestSaved.getSalaryFrom());
        }
        return response;
    }

    @Override
    public ResponseWithTotalPage getAllRecruitmentRequestByCreator(int id, int pageNo,
                                                                   int pageSize) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<RecruitmentRequest> pageResult = recruitmentRequestRepository.findByCreator(employee, pageable);
        List<Object> result = new ArrayList<>();
        ResponseWithTotalPage responseWithTotalPages = null;

        if (pageResult.hasContent()) {
            for (RecruitmentRequest recruitmentRequest : pageResult.getContent()) {
                RecruitmentRequestResponse response = modelMapper.map(recruitmentRequest,
                        RecruitmentRequestResponse.class);
                if (recruitmentRequest.getSalaryTo() != null) {
                    response.setSalaryDetail(
                            (recruitmentRequest.getSalaryFrom() + " - " + recruitmentRequest.getSalaryTo())
                                    .trim());
                } else {
                    response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
                }
                result.add(response);
                responseWithTotalPages = ResponseWithTotalPage
                        .builder()
                        .totalPage(pageResult.getTotalPages())
                        .responseList(result)
                        .build();
            }
        } else
            throw new ListEmptyException(RecruitmentRequestErrorMessage.LIST_RECRUITMENT_REQUEST_EMPTY_EXCEPTION);
        return responseWithTotalPages;
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
                if (recruitmentRequest.getSalaryTo() != null) {
                    response.setSalaryDetail(
                            (recruitmentRequest.getSalaryFrom() + " - " + recruitmentRequest.getSalaryTo())
                                    .trim());
                } else {
                    response.setSalaryDetail(recruitmentRequest.getSalaryFrom());
                }
                result.add(response);
            }
        }
        return result;
    }

    @Override
    public RecruitmentSearchCategoryDTO searchCategory() {
        RecruitmentSearchCategoryDTO recruitmentSearchCategoryDTO = RecruitmentSearchCategoryDTO.builder()
                .jobTitle(recruitmentRequestRepository.getDistinctByPosition())
                .industry(recruitmentRequestRepository.getDistinctByIndustry())
                .province(cityRepository.getAllCityName())
                .build();
        return recruitmentSearchCategoryDTO;
    }
}
