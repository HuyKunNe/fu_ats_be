package com.fu.fuatsbe.serviceImp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.CVScreeningDTO;
import com.fu.fuatsbe.DTO.JobApplyCreateDTO;
import com.fu.fuatsbe.constant.candidate.CandidateErrorMessage;
import com.fu.fuatsbe.constant.city.CityErrorMessage;
import com.fu.fuatsbe.constant.cv.CVStatus;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.job_apply.EducationLevel;
import com.fu.fuatsbe.constant.job_apply.Experience;
import com.fu.fuatsbe.constant.job_apply.JobApplyErrorMessage;
import com.fu.fuatsbe.constant.job_apply.JobApplyStatus;
import com.fu.fuatsbe.constant.job_apply.ScreeningStatus;
import com.fu.fuatsbe.constant.recruitmentRequest.RecruitmentRequestErrorMessage;
import com.fu.fuatsbe.entity.CV;
import com.fu.fuatsbe.entity.CVScreening;
import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.City;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.JobApply;
import com.fu.fuatsbe.entity.RecruitmentRequest;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.exceptions.NotValidException;
import com.fu.fuatsbe.repository.CVScreeningRepository;
import com.fu.fuatsbe.repository.CandidateRepository;
import com.fu.fuatsbe.repository.CityRepository;
import com.fu.fuatsbe.repository.CvRepository;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.JobApplyRepository;
import com.fu.fuatsbe.repository.RecruitmentRequestRepository;
import com.fu.fuatsbe.response.JobApplyResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
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
    private final CityRepository cityRepository;
    private final CVScreeningRepository cvScreeningRepository;

    public final List<String> listExperiences = Arrays.asList(Experience.LESS_THEN_1_YEAR, Experience.ONE_YEAR,
            Experience.TWO_YEARS, Experience.THREE_YEARS, Experience.FOUR_YEARS, Experience.FIVE_YEARS,
            Experience.OVER_5_YEARS, Experience.OVER_8_YEARS, Experience.OVER_10_YEARS);

    @Override
    public ResponseWithTotalPage<JobApplyResponse> getAllJobApplies(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<JobApply> pageResult = jobApplyRepository.findAll(pageable);
        List<JobApplyResponse> list = new ArrayList<JobApplyResponse>();
        ResponseWithTotalPage<JobApplyResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                list.add(jobApplyResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(JobApplyErrorMessage.LIST_IS_EMPTY);
        return result;
    }

    @Override
    public ResponseWithTotalPage<JobApplyResponse> getJobApplyByCandidate(int candidateId, int pageNo, int pageSize) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<JobApply> pageResult = jobApplyRepository.findByCandidate(candidate.getId(), pageable);
        List<JobApplyResponse> list = new ArrayList<JobApplyResponse>();
        ResponseWithTotalPage<JobApplyResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                list.add(jobApplyResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(JobApplyErrorMessage.LIST_IS_EMPTY);
        return result;
    }

    @Override
    public ResponseWithTotalPage<JobApplyResponse> getAllJobAppliesByRecruitmentRequest(int requestId, int pageNo,
            int pageSize) {
        RecruitmentRequest recruitmentRequest = recruitmentRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(
                        RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<JobApply> pageResult = jobApplyRepository.findByRecruitmentRequest(recruitmentRequest, pageable);
        List<JobApplyResponse> list = new ArrayList<JobApplyResponse>();
        ResponseWithTotalPage<JobApplyResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                list.add(jobApplyResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
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

        Candidate candidate = candidateRepository.findById(createDTO.getCandidateId())
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));

        CV cv = CV.builder().candidate(candidate).linkCV(createDTO.getLinkCV())
                .status(CVStatus.ACTIVE).build();

        CV cvSaved = cvRepository.save(cv);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDate dateFormat = LocalDate.parse(date.toString(), format);

        City city = cityRepository.findByName(createDTO.getCityName())
                .orElseThrow(() -> new NotFoundException(CityErrorMessage.NOT_FOUND));

        JobApply jobApply = JobApply.builder().date(Date.valueOf(dateFormat)).cities(city)
                .educationLevel(createDTO.getEducationLevel())
                .foreignLanguage(createDTO.getForeignLanguage())
                .status(JobApplyStatus.PENDING)
                .screeningStatus(ScreeningStatus.PASS)
                .candidate(candidate).recruitmentRequest(recruitmentRequest).cv(cvSaved).build();

        JobApply jobApplySaved = jobApplyRepository.save(jobApply);

        if (!screeningCV(jobApplySaved)) {
            jobApply.setScreeningStatus(ScreeningStatus.NOT_PASS);
            jobApplyRepository.save(jobApplySaved);
        }

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
    public ResponseWithTotalPage<JobApplyResponse> getAllPendingJobApplies(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<JobApply> pageResult = jobApplyRepository.findByStatus(JobApplyStatus.PENDING, pageable);
        List<JobApplyResponse> list = new ArrayList<JobApplyResponse>();
        ResponseWithTotalPage<JobApplyResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                list.add(jobApplyResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(JobApplyErrorMessage.LIST_IS_EMPTY);
        return result;
    }

    @Override
    public ResponseWithTotalPage<JobApplyResponse> getAllApprovedJobApplies(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<JobApply> pageResult = jobApplyRepository.findByStatus(JobApplyStatus.APPROVED, pageable);
        List<JobApplyResponse> list = new ArrayList<JobApplyResponse>();
        ResponseWithTotalPage<JobApplyResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                list.add(jobApplyResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(JobApplyErrorMessage.LIST_IS_EMPTY);
        return result;
    }

    @Override
    public ResponseWithTotalPage<JobApplyResponse> getAllCancelJobApplies(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<JobApply> pageResult = jobApplyRepository.findByStatus(JobApplyStatus.CANCELED, pageable);
        List<JobApplyResponse> list = new ArrayList<JobApplyResponse>();
        ResponseWithTotalPage<JobApplyResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                list.add(jobApplyResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
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
        jobApply.setStatus(JobApplyStatus.CANCELED);

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

    @Override
    public ResponseWithTotalPage<JobApplyResponse> getJobApplyByDepartment(int departmentId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<JobApply> pageResult = jobApplyRepository.getJobApplyByDepartment(departmentId, pageable);
        List<JobApplyResponse> list = new ArrayList<JobApplyResponse>();
        ResponseWithTotalPage<JobApplyResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                list.add(jobApplyResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(JobApplyErrorMessage.LIST_IS_EMPTY);
        return result;
    }

    @Override
    public CVScreening cvScreeningSetting(CVScreeningDTO screeningDTO) {

        CVScreening cvScreening = cvScreeningRepository.findTopByOrderByIdDesc();
        if (screeningDTO.getCity() + screeningDTO.getEducationLevel() + screeningDTO.getExperience()
                + screeningDTO.getForeignLanguage() != 100) {
            throw new NotValidException("Total must be equal to 100");
        } else {
            cvScreening.setCity(screeningDTO.getCity());
            cvScreening.setEducationLevel(screeningDTO.getEducationLevel());
            cvScreening.setExperience(screeningDTO.getExperience());
            cvScreening.setForeignLanguage(screeningDTO.getForeignLanguage());
        }
        return cvScreeningRepository.save(cvScreening);

    }

    public boolean screeningCV(JobApply jobApply) {
        int total = 0;

        CVScreening cvScreening = cvScreeningRepository.findTopByOrderByIdDesc();

        int educationLevel = EducationLevel.LIST_EDUCATION_LEVEL.indexOf(jobApply.getEducationLevel());
        int educationLevelRequired = EducationLevel.LIST_EDUCATION_LEVEL
                .indexOf(jobApply.getRecruitmentRequest().getEducationLevel());

        total += (educationLevel >= educationLevelRequired ? cvScreening.getEducationLevel() : 0);

        String foreignLanguageRequired = jobApply.getForeignLanguage();
        String[] foreignLanguages = jobApply.getForeignLanguage().split(",");
        int foreignLanguageContain = 0;

        for (String foreignLanguage : foreignLanguages) {
            foreignLanguageContain += foreignLanguageRequired.contains(foreignLanguage.trim()) ? 1 : 0;
        }

        total += ((foreignLanguageContain / foreignLanguages.length) * cvScreening.getForeignLanguage());

        if (jobApply.getCities().getId() == jobApply.getRecruitmentRequest().getCities().getId()) {
            total += cvScreening.getCity();
        }

        int experience = listExperiences.indexOf(jobApply.getExperience());
        int experienceRequired = listExperiences.indexOf(jobApply.getRecruitmentRequest().getExperience());

        total += (experience >= experienceRequired ? cvScreening.getExperience() : 0);

        return total >= cvScreening.getPercentRequired() ? true : false;

    }

    @Override
    public ResponseWithTotalPage<JobApplyResponse> getJobApplyNotReject(int recruitmentRequest, int pageNo,
            int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        RecruitmentRequest request = recruitmentRequestRepository.findById(recruitmentRequest).orElseThrow(
                () -> new NotFoundException(RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));
        Page<JobApply> pageResult = jobApplyRepository.findByRecruitmentRequestAndStatusNotLike(request,
                JobApplyStatus.REJECTED, pageable);
        List<JobApplyResponse> list = new ArrayList<JobApplyResponse>();
        ResponseWithTotalPage<JobApplyResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                list.add(jobApplyResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(JobApplyErrorMessage.LIST_IS_EMPTY);
        return result;
    }

    @Override
    public CVScreening getCVScreening() {
        CVScreening cvScreening = cvScreeningRepository.findTopByOrderByIdDesc();
        return cvScreening;
    }

    @Override
    public ResponseWithTotalPage<JobApplyResponse> getJobApplyPassScreenig(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<JobApply> pageResult = jobApplyRepository.findByScreeningStatus(
                ScreeningStatus.NOT_PASS, pageable);
        List<JobApplyResponse> list = new ArrayList<JobApplyResponse>();
        ResponseWithTotalPage<JobApplyResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                list.add(jobApplyResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(JobApplyErrorMessage.LIST_IS_EMPTY);
        return result;
    }

    @Override
    public ResponseWithTotalPage<JobApplyResponse> getAllFailedJobApplies(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<JobApply> pageResult = jobApplyRepository.findByScreeningStatusLikeOrStatusLike(
                ScreeningStatus.NOT_PASS, JobApplyStatus.REJECTED, pageable);
        List<JobApplyResponse> list = new ArrayList<JobApplyResponse>();
        ResponseWithTotalPage<JobApplyResponse> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (JobApply jobApply : pageResult.getContent()) {
                JobApplyResponse jobApplyResponse = modelMapper.map(jobApply, JobApplyResponse.class);
                list.add(jobApplyResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(JobApplyErrorMessage.LIST_IS_EMPTY);
        return result;
    }

}
