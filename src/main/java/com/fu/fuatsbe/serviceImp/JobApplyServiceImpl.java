package com.fu.fuatsbe.serviceImp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.CVScreeningDTO;
import com.fu.fuatsbe.DTO.JobApplyByEmployeeDTO;
import com.fu.fuatsbe.DTO.JobApplyCreateDTO;
import com.fu.fuatsbe.DTO.ListJobApplyByEmployee;
import com.fu.fuatsbe.constant.candidate.CandidateErrorMessage;
import com.fu.fuatsbe.constant.candidate.CandidateStatus;
import com.fu.fuatsbe.constant.city.CityErrorMessage;
import com.fu.fuatsbe.constant.cv.CVSource;
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
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.JobApply;
import com.fu.fuatsbe.entity.PlanDetail;
import com.fu.fuatsbe.entity.Position;
import com.fu.fuatsbe.entity.RecruitmentPlan;
import com.fu.fuatsbe.entity.RecruitmentRequest;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.exceptions.NotValidException;
import com.fu.fuatsbe.repository.CVScreeningRepository;
import com.fu.fuatsbe.repository.CandidateRepository;
import com.fu.fuatsbe.repository.CityRepository;
import com.fu.fuatsbe.repository.CvRepository;
import com.fu.fuatsbe.repository.DepartmentRepository;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.JobApplyRepository;
import com.fu.fuatsbe.repository.PlanDetailRepository;
import com.fu.fuatsbe.repository.RecruitmentPlanRepository;
import com.fu.fuatsbe.repository.RecruitmentRequestRepository;
import com.fu.fuatsbe.response.JobApplyResponse;
import com.fu.fuatsbe.response.ReportDTO;
import com.fu.fuatsbe.response.ReportDetailDTO;
import com.fu.fuatsbe.response.ReportGroupByDepartment;
import com.fu.fuatsbe.response.ReportGroupByJobRequest;
import com.fu.fuatsbe.response.ReportGroupByPlan;
import com.fu.fuatsbe.response.ReportGroupByPlanDetail;
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
    private final RecruitmentPlanRepository recruitmentPlanRepository;
    private final PlanDetailRepository planDetailRepository;
    private final DepartmentRepository departmentRepository;
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

        candidate.getPositions().add(recruitmentRequest.getPosition());

        candidateRepository.save(candidate);

        Optional<CV> cv = cvRepository.findById(createDTO.getCvId());
        CV cvSaved = null;
        if (cv.isPresent()) {
            cv.get().getPositions().add(recruitmentRequest.getPosition());
            cvSaved = cvRepository.save(cv.get());
        } else {
            Collection<Position> list = new ArrayList<Position>();
            list.add(recruitmentRequest.getPosition());
            CV newCV = CV.builder().candidate(candidate).linkCV(createDTO.getLinkCV())
                    .title(createDTO.getTitleCV())
                    .positions(list)
                    .source(CVSource.CKHR)
                    .recommendPositions("N/A")
                    .status(CVStatus.ACTIVE).build();
            cvSaved = cvRepository.save(newCV);
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDate dateFormat = LocalDate.parse(date.toString(), format);

        City city = cityRepository.findByName(createDTO.getCityName())
                .orElseThrow(() -> new NotFoundException(CityErrorMessage.NOT_FOUND));

        JobApply jobApply = JobApply.builder().date(Date.valueOf(dateFormat)).cities(city)
                .educationLevel(createDTO.getEducationLevel())
                .foreignLanguage(createDTO.getForeignLanguage())
                .experience(createDTO.getExperience())
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

    public int multiplePercent(int noOfTurn, int value) {
        int result = value;
        if (noOfTurn <= 0) {
            return result;
        } else {
            return multiplePercent(noOfTurn - 1, value / 2);
        }
    }

    public boolean screeningCV(JobApply jobApply) {
        int total = 0;

        CVScreening cvScreening = cvScreeningRepository.findTopByOrderByIdDesc();

        int educationLevel = EducationLevel.LIST_EDUCATION_LEVEL.indexOf(jobApply.getEducationLevel());
        int educationLevelRequired = EducationLevel.LIST_EDUCATION_LEVEL
                .indexOf(jobApply.getRecruitmentRequest().getEducationLevel());

        int noOfTurn = educationLevelRequired - educationLevel;

        total += (educationLevel >= educationLevelRequired ? cvScreening.getEducationLevel()
                : (multiplePercent(noOfTurn, cvScreening.getEducationLevel())));

        String foreignLanguageRequired = jobApply.getForeignLanguage();
        String[] foreignLanguages = jobApply.getForeignLanguage().split(",");
        int foreignLanguageContain = 0;

        for (String foreignLanguage : foreignLanguages) {
            foreignLanguageContain += foreignLanguageRequired.contains(foreignLanguage.trim()) ? 1 : 0;
        }

        total += ((foreignLanguageContain / foreignLanguages.length) * cvScreening.getForeignLanguage());

        if (jobApply.getCities().getId() == jobApply.getRecruitmentRequest().getCities().getId()
                || jobApply.getRecruitmentRequest().getTypeOfWork().equalsIgnoreCase("Work from home")) {
            total += cvScreening.getCity();
        }

        int experience = listExperiences.indexOf(jobApply.getExperience());
        int experienceRequired = listExperiences.indexOf(jobApply.getRecruitmentRequest().getExperience());

        total += (experience >= experienceRequired ? cvScreening.getExperience() : 0);
        System.out.println("total: " + total);

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
    public ResponseWithTotalPage<JobApplyResponse> getJobApplyPassScreening(int requestId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        RecruitmentRequest request = recruitmentRequestRepository.findById(requestId).orElseThrow(
                () -> new NotFoundException(RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));

        Page<JobApply> pageResult = jobApplyRepository
                .findByScreeningStatusAndRecruitmentRequest(ScreeningStatus.PASS, request, pageable);
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

    @Override
    public boolean checkApplyByRecruitmentRequestAndCandidate(int requestId, int candidateId) {
        boolean result = false;
        RecruitmentRequest request = recruitmentRequestRepository.findById(requestId).orElseThrow(
                () -> new NotFoundException(RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));

        result = jobApplyRepository.countByRecruitmentRequestAndCandidate(request, candidate) > 0;
        return result;
    }

    @Override
    public ResponseWithTotalPage<JobApplyResponse> getJobApplyFailScreening(int requestId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        RecruitmentRequest request = recruitmentRequestRepository.findById(requestId).orElseThrow(
                () -> new NotFoundException(RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));

        Page<JobApply> pageResult = jobApplyRepository
                .findByScreeningStatusAndRecruitmentRequest(ScreeningStatus.NOT_PASS, request, pageable);
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
    public List<JobApplyResponse> createJobApplyByEmployee(ListJobApplyByEmployee listJobApplyByEmployee) {

        List<JobApplyResponse> result = new ArrayList<JobApplyResponse>();

        Employee employee = employeeRepository.findById(listJobApplyByEmployee.getEmployeeId())
                .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));

        RecruitmentRequest recruitmentRequest = recruitmentRequestRepository
                .findById(listJobApplyByEmployee.getRequestId())
                .orElseThrow(() -> new NotFoundException(
                        RecruitmentRequestErrorMessage.RECRUITMENT_REQUEST_NOT_FOUND_EXCEPTION));

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDate dateFormat = LocalDate.parse(date.toString(), format);

        City city = recruitmentRequest.getCities();

        for (JobApplyByEmployeeDTO jobApplyByEmployeeDTO : listJobApplyByEmployee.getListJobApplyByEmployee()) {

            Optional<Candidate> candidate = candidateRepository.findByEmail(jobApplyByEmployeeDTO.getEmail());

            Collection<Position> list = new ArrayList<Position>();
            list.add(recruitmentRequest.getPosition());

            CV cv = CV.builder()
                    .linkCV(jobApplyByEmployeeDTO.getLinkCV())
                    .source(jobApplyByEmployeeDTO.getSource())
                    .positions(list)
                    .recommendPositions("N/A")
                    .status(CVStatus.ACTIVE)
                    .build();

            JobApply jobApply = JobApply.builder().date(Date.valueOf(dateFormat)).cities(city)
                    .status(JobApplyStatus.APPROVED)
                    .applier(employee)
                    .screeningStatus(ScreeningStatus.PASS)
                    .recruitmentRequest(recruitmentRequest)
                    .build();

            if (candidate.isPresent()) {
                candidate.get().getPositions().add(recruitmentRequest.getPosition());
                Candidate candidateSave = candidateRepository.save(candidate.get());
                cv.setTitle(candidateSave.getName() + " CV");
                cv.setCandidate(candidateSave);
                jobApply.setCandidate(candidateSave);

            } else {
                Candidate newCandidate = Candidate.builder().email(jobApplyByEmployeeDTO.getEmail())
                        .positions(list)
                        .name(jobApplyByEmployeeDTO.getName())
                        .status(CandidateStatus.ACTIVATED)
                        .build();

                Candidate candidateSaved = candidateRepository.save(newCandidate);
                cv.setTitle(candidateSaved.getName() + " CV");
                cv.setCandidate(candidateSaved);
                jobApply.setCandidate(candidateSaved);
            }

            CV cvSaved = cvRepository.save(cv);
            jobApply.setCv(cvSaved);

            JobApply jobApplySaved = jobApplyRepository.save(jobApply);

            JobApplyResponse jobApplyResponse = modelMapper.map(jobApplySaved, JobApplyResponse.class);

            result.add(jobApplyResponse);
        }

        return result;
    }

    @Override
    public List<ReportGroupByDepartment> getReport(String year, String departmentName) {
        List<ReportGroupByDepartment> result = new ArrayList<ReportGroupByDepartment>();
        List<ReportDTO> list = jobApplyRepository.getReport(year, departmentName);

        if (list.size() == 0) {
            throw new ListEmptyException("No data for " + year);
        }

        List<ReportGroupByJobRequest> jobRequests = new ArrayList<ReportGroupByJobRequest>();
        List<ReportGroupByPlanDetail> planDetails = new ArrayList<ReportGroupByPlanDetail>();
        List<ReportGroupByPlan> plans = new ArrayList<ReportGroupByPlan>();
        List<ReportDetailDTO> details = new ArrayList<ReportDetailDTO>();

        int lastRequestId = list.get(0).getJobRequestId();
        int lastPlanId = list.get(0).getPlanId();
        int lastPlanDetailId = list.get(0).getPlanDetailId();
        int lastDepartmentId = list.get(0).getDepartmentId();

        list.add(list.get(list.size() - 1));

        int totalByJobRequest = 0;
        int totalByPlanDetail = 0;
        int totalByPlan = 0;
        int totalByDepartment = 0;

        int totalRowByDepartment = 0;
        int totalRowByPlan = 0;
        int totalRowByPlanDetail = 0;
        int totalRowByJobRequest = 0;

        ReportGroupByDepartment reportGroupByDepartment = new ReportGroupByDepartment();
        ReportGroupByPlan reportGroupByPlan = new ReportGroupByPlan();
        ReportGroupByPlanDetail reportGroupByPlanDetail = new ReportGroupByPlanDetail();
        ReportGroupByJobRequest reportGroupByJobRequest = new ReportGroupByJobRequest();

        int index = 1;
        for (ReportDTO reportDTO : list) {

            ReportDetailDTO detail = ReportDetailDTO.builder().source(reportDTO.getSource())
                    .totalCV(reportDTO.getTotalCV())
                    .totalAcceptableCV(reportDTO.getTotalAcceptableCV())
                    .totalJoinInterview(reportDTO.getTotalJoinInterview())
                    .totalPassInterview(reportDTO.getTotalPassInterview())
                    .build();

            if (lastRequestId != reportDTO.getJobRequestId() || index >= list.size()) {

                RecruitmentRequest recruitmentRequest = recruitmentRequestRepository.findById(lastRequestId).get();

                totalByJobRequest = details.size();

                totalRowByJobRequest = totalByJobRequest + 1;

                reportGroupByJobRequest = ReportGroupByJobRequest.builder()
                        .details(details)
                        .totalDetailByJobRequest(totalByJobRequest)
                        .totalRowByJobRequest(totalRowByJobRequest)
                        .recruitmentRequestName(recruitmentRequest.getName())
                        .build();
                jobRequests.add(reportGroupByJobRequest);
                details = new ArrayList<ReportDetailDTO>();
                details.add(detail);
                totalByJobRequest = 0;
                totalRowByJobRequest = 0;
                lastRequestId = reportDTO.getJobRequestId();
            } else {
                details.add(detail);
            }

            if (lastPlanDetailId != reportDTO.getPlanDetailId() || index >= list.size()) {

                PlanDetail planDetail = planDetailRepository.findById(lastPlanDetailId).get();

                for (ReportGroupByJobRequest jobRequest : jobRequests) {

                    totalByPlanDetail += jobRequest.getTotalDetailByJobRequest();
                    totalRowByPlanDetail += jobRequest.getTotalRowByJobRequest();
                }

                reportGroupByPlanDetail = ReportGroupByPlanDetail.builder()
                        .jobRequests(jobRequests)
                        .totalDetailByPlanDetail(totalByPlanDetail)
                        .totalRowByPlanDetail(totalRowByPlanDetail + 1)
                        .planDetailName(planDetail.getName())
                        .build();

                planDetails.add(reportGroupByPlanDetail);

                jobRequests = new ArrayList<>();

                totalByPlanDetail = 0;
                totalRowByPlanDetail = 0;

                lastPlanDetailId = reportDTO.getPlanDetailId();
            }

            if (lastPlanId != reportDTO.getPlanId() || index >= list.size()) {
                RecruitmentPlan recruitmentPlan = recruitmentPlanRepository.findById(lastPlanId).get();

                for (ReportGroupByPlanDetail planDetail : planDetails) {
                    totalByPlan += planDetail.getTotalDetailByPlanDetail();
                    totalRowByPlan += planDetail.getTotalRowByPlanDetail();
                }

                reportGroupByPlan = ReportGroupByPlan.builder()
                        .recruitmentPlanName(recruitmentPlan.getName())
                        .totalDetailByPlan(totalByPlan)
                        .totalRowByPlan(totalRowByPlan + 1)
                        .planDetails(planDetails)
                        .build();

                plans.add(reportGroupByPlan);
                planDetails = new ArrayList<>();

                totalByPlan = 0;
                totalRowByPlan = 0;

                lastPlanId = reportDTO.getPlanId();

            }

            if (lastDepartmentId != reportDTO.getDepartmentId() || index >= list.size()) {

                Department department = departmentRepository.findById(lastDepartmentId).get();

                for (ReportGroupByPlan plan : plans) {
                    totalByDepartment += plan.getTotalDetailByPlan();
                    totalRowByDepartment += plan.getTotalRowByPlan();
                }

                reportGroupByDepartment = ReportGroupByDepartment.builder()
                        .departmentName(department.getName())
                        .totalDetailByDepartment(totalByDepartment)
                        .totalRowByDepartment(totalRowByDepartment + 1)
                        .recruitmentPlans(plans)
                        .build();

                result.add(reportGroupByDepartment);
                reportGroupByDepartment = new ReportGroupByDepartment();

                plans = new ArrayList<>();
                totalByDepartment = 0;
                totalRowByDepartment = 0;

                lastDepartmentId = reportDTO.getDepartmentId();

            }
            ++index;
        }
        return result;
    }
}
