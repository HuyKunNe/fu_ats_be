package com.fu.fuatsbe.serviceImp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.JobApplyCreateDTO;
import com.fu.fuatsbe.constant.candidate.CandidateErrorMessage;
import com.fu.fuatsbe.constant.city.CityErrorMessage;
import com.fu.fuatsbe.constant.cv.CVStatus;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.job_apply.JobApplyErrorMessage;
import com.fu.fuatsbe.constant.job_apply.JobApplyStatus;
import com.fu.fuatsbe.constant.postion.PositionErrorMessage;
import com.fu.fuatsbe.constant.recruitmentRequest.RecruitmentRequestErrorMessage;
import com.fu.fuatsbe.entity.CV;
import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.City;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.JobApply;
import com.fu.fuatsbe.entity.Position;
import com.fu.fuatsbe.entity.RecruitmentRequest;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.repository.CandidateRepository;
import com.fu.fuatsbe.repository.CityRepository;
import com.fu.fuatsbe.repository.CvRepository;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.JobApplyRepository;
import com.fu.fuatsbe.repository.PositionRepository;
import com.fu.fuatsbe.repository.RecruitmentRequestRepository;
import com.fu.fuatsbe.response.JobApplyResponse;
import com.fu.fuatsbe.service.JobApplyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobApplyServiceImpl implements JobApplyService {

    private final JobApplyRepository jobApplyRepository;
    private final CandidateRepository candidateRepository;
    private final RecruitmentRequestRepository recruitmentRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final CvRepository cvRepository;
    private final ModelMapper modelMapper;
    private final PositionRepository positionRepository;
    private final CityRepository cityRepository;

    @Override
    public List<JobApplyResponse> getAllJobApplies(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<JobApply> pageResult = jobApplyRepository.findAll(pageable);

        List<JobApplyResponse> result = new ArrayList<JobApplyResponse>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                result.add(jobApplyResponse);
            }
        } else
            throw new ListEmptyException(JobApplyErrorMessage.LIST_IS_EMPTY);
        return result;
    }

    @Override
    public List<JobApplyResponse> getJobApplyByCandidate(int candidateId, int pageNo, int pageSize) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<JobApply> pageResult = jobApplyRepository.findByCandidate(candidate, pageable);

        List<JobApplyResponse> result = new ArrayList<JobApplyResponse>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                result.add(jobApplyResponse);
            }
        } else
            throw new ListEmptyException(JobApplyErrorMessage.LIST_IS_EMPTY);
        return result;
    }

    @Override
    public List<JobApplyResponse> getAllJobAppliesByRecruitmentRequest(int requestId, int pageNo, int pageSize) {
        RecruitmentRequest recruitmentRequest = recruitmentRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(
                        RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<JobApply> pageResult = jobApplyRepository.findByRecruitmentRequest(recruitmentRequest, pageable);

        List<JobApplyResponse> result = new ArrayList<JobApplyResponse>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                result.add(jobApplyResponse);
            }
        } else
            throw new ListEmptyException(JobApplyErrorMessage.LIST_IS_EMPTY);
        return result;
    }

    @Override
    public JobApplyResponse createJobApply(JobApplyCreateDTO createDTO) {

        RecruitmentRequest recruitmentRequest = recruitmentRequestRepository
                .findById(createDTO.getRecruitmentRequestId())
                .orElseThrow(() -> new NotFoundException(
                        RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));

        List<Position> positions = new ArrayList<>();

        for (String positionName : createDTO.getPositionName()) {
            Position position = positionRepository.findPositionByName(positionName)
                    .orElseThrow(() -> new NotFoundException(PositionErrorMessage.POSITION_NOT_EXIST));
            positions.add(position);
        }

        Candidate candidate = candidateRepository.findById(createDTO.getCandidateId())
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));

        CV cv = CV.builder().candidate(candidate).linkCV(createDTO.getLinkCV()).positions(positions)
                .status(CVStatus.ACTIVE).build();

        CV cvSaved = cvRepository.save(cv);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDate dateFormat = LocalDate.parse(date.toString(), format);

        List<City> cities = new ArrayList<>();
        for (String cityName : createDTO.getCityName()) {
            City city = cityRepository.findByName(cityName)
                    .orElseThrow(() -> new NotFoundException(CityErrorMessage.NOT_FOUND));
            cities.add(city);
        }

        JobApply jobApply = JobApply.builder().date(Date.valueOf(dateFormat)).cities(cities)
                .educationLevel(createDTO.getEducationLevel())
                .foreignLanguage(createDTO.getForeignLanguage())
                .status(JobApplyStatus.PEDNING)
                .candidate(candidate).recruitmentRequest(recruitmentRequest).cv(cvSaved).build();

        JobApply jobApplySaved = jobApplyRepository.save(jobApply);

        JobApplyResponse jobApplyResponse = modelMapper.map(jobApplySaved, JobApplyResponse.class);

        return jobApplyResponse;

    }

    @Override
    public JobApplyResponse getJobApplyById(int id) {
        JobApply jobApply = jobApplyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(JobApplyErrorMessage.JOB_APPLY_NOT_FOUND));

        JobApplyResponse response = modelMapper.map(jobApply, JobApplyResponse.class);
        return response;
    }

    @Override
    public List<JobApplyResponse> getAllPendingJobApplies(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<JobApply> pageResult = jobApplyRepository.findByStatus(JobApplyStatus.PEDNING, pageable);

        List<JobApplyResponse> result = new ArrayList<JobApplyResponse>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                result.add(jobApplyResponse);
            }
        } else
            throw new ListEmptyException(JobApplyErrorMessage.LIST_IS_EMPTY);
        return result;
    }

    @Override
    public List<JobApplyResponse> getAllApprovedJobApplies(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<JobApply> pageResult = jobApplyRepository.findByStatus(JobApplyStatus.APPROVED, pageable);

        List<JobApplyResponse> result = new ArrayList<JobApplyResponse>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                result.add(jobApplyResponse);
            }
        } else
            throw new ListEmptyException(JobApplyErrorMessage.LIST_IS_EMPTY);
        return result;
    }

    @Override
    public List<JobApplyResponse> getAllCancelJobApplies(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<JobApply> pageResult = jobApplyRepository.findByStatus(JobApplyStatus.CANCLED, pageable);

        List<JobApplyResponse> result = new ArrayList<JobApplyResponse>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                result.add(jobApplyResponse);
            }
        } else
            throw new ListEmptyException(JobApplyErrorMessage.LIST_IS_EMPTY);
        return result;
    }

    @Override
    public JobApplyResponse cancelJobApply(int id, int employeeId) {
        JobApply jobApply = jobApplyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(JobApplyErrorMessage.JOB_APPLY_NOT_FOUND));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));

        jobApply.setApplier(employee);
        jobApply.setStatus(JobApplyStatus.CANCLED);

        JobApply jobApplySaved = jobApplyRepository.save(jobApply);

        JobApplyResponse response = modelMapper.map(jobApplySaved, JobApplyResponse.class);
        return response;
    }

    @Override
    public JobApplyResponse approvedJobApply(int id, int employeeId) {
        JobApply jobApply = jobApplyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(JobApplyErrorMessage.JOB_APPLY_NOT_FOUND));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));

        jobApply.setApplier(employee);
        jobApply.setStatus(JobApplyStatus.APPROVED);

        JobApply jobApplySaved = jobApplyRepository.save(jobApply);

        JobApplyResponse response = modelMapper.map(jobApplySaved, JobApplyResponse.class);
        return response;
    }

}
