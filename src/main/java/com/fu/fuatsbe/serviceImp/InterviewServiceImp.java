package com.fu.fuatsbe.serviceImp;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.persistence.Tuple;

import com.fu.fuatsbe.constant.candidate.CandidateStatus;

import com.fu.fuatsbe.entity.*;
import com.fu.fuatsbe.repository.*;
import com.fu.fuatsbe.response.NameAndStatusResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.CancelInterviewDTO;
import com.fu.fuatsbe.DTO.InterviewCreateDTO;
import com.fu.fuatsbe.DTO.InterviewUpdateDTO;
import com.fu.fuatsbe.DTO.NotificationCreateDTO;
import com.fu.fuatsbe.DTO.SendNotificationDTO;
import com.fu.fuatsbe.constant.candidate.CandidateErrorMessage;
import com.fu.fuatsbe.constant.department.DepartmentErrorMessage;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.interview.InterviewErrorMessage;
import com.fu.fuatsbe.constant.interview.InterviewRequestStatus;
import com.fu.fuatsbe.constant.interview_employee.InterviewEmployeeRequestStatus;
import com.fu.fuatsbe.constant.job_apply.JobApplyErrorMessage;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.exceptions.NotValidException;
import com.fu.fuatsbe.exceptions.PermissionException;
import com.fu.fuatsbe.response.InterviewResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.InterviewService;
import com.fu.fuatsbe.service.NotificationService;
import com.fu.fuatsbe.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewServiceImp implements InterviewService {
    private final InterviewRepository interviewRepository;

    private final CandidateRepository candidateRepository;

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final JobApplyRepository jobApplyRepository;
    private final NotificationRepository notificationRepository;

    private final NotificationService notificationService;
    private final EmailService emailService;

    private final InterviewEmployeeRepository interviewEmployeeRepository;

    private final ModelMapper modelMapper;

    @Override
    public InterviewResponse createInterview(InterviewCreateDTO interviewCreateDTO) throws MessagingException {
        Candidate candidate = candidateRepository.findById(interviewCreateDTO.getCandidateId())
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));

        List<Employee> employeeList = new ArrayList<>();
        List<Integer> intervieweeIdList = new ArrayList<>();
        for (Integer employeeId : interviewCreateDTO.getEmployeeId()) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
            employeeList.add(employee);
            intervieweeIdList.add(employeeId);
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate localDate = LocalDate.parse(interviewCreateDTO.getDate(), dateFormater);
        LocalTime localTime = LocalTime.parse(interviewCreateDTO.getTime(), timeFormatter);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        String dateInput = localDateTime.format(dateTimeFormatter);
        Timestamp dateInterview = Timestamp.valueOf(dateInput);
        LocalDate presentDate = LocalDate.parse(LocalDate.now().toString(), dateFormater);

        if (localDate.isBefore(presentDate)) {
            throw new PermissionException(InterviewErrorMessage.DATE_NOT_VALID);
        }
        JobApply jobApply = jobApplyRepository.getJobAppliesByRecruitmentAndCandidate(
                interviewCreateDTO.getRecruitmentRequestId(), interviewCreateDTO.getCandidateId())
                .orElseThrow(() -> new NotValidException(JobApplyErrorMessage.CANDIDATE_NOT_APPLY));
        Interview interview = Interview.builder()
                .subject(interviewCreateDTO.getSubject())
                .purpose(interviewCreateDTO.getPurpose())
                .date(dateInterview)
                .address(interviewCreateDTO.getAddress())
                .room(interviewCreateDTO.getRoom())
                .linkMeeting(interviewCreateDTO.getLinkMeeting())
                .round(interviewCreateDTO.getRound())
                .description(interviewCreateDTO.getDescription())
                .status(InterviewRequestStatus.PENDING)
                .type(interviewCreateDTO.getType())
                .candidate(candidate)
                .jobApply(jobApply)
                .build();
        Interview savedInterview = interviewRepository.save(interview);

        List<InterviewEmployee> listInterviewEmployees = new ArrayList<>();

        for (Employee emp : employeeList) {
            InterviewEmployee interviewEmployee = InterviewEmployee.builder()
                    .employee(emp)
                    .interview(savedInterview)
                    .build();
            InterviewEmployee interviewEmployeeSaved = interviewEmployeeRepository.save(interviewEmployee);
            listInterviewEmployees.add(interviewEmployeeSaved);
        }

        SendNotificationDTO sendNotificationDTO = SendNotificationDTO.builder()
                .link(savedInterview.getLinkMeeting())
                .room(savedInterview.getRoom())
                .address(savedInterview.getAddress())
                .date(savedInterview.getDate())
                .candidate(candidate)
                .IntervieweeID(intervieweeIdList)
                .interview(savedInterview)
                .build();
        notificationService.sendNotificationForInterview(sendNotificationDTO);

        InterviewResponse response = InterviewResponse.builder()
                .id(savedInterview.getId())
                .subject(savedInterview.getSubject())
                .purpose(savedInterview.getPurpose())
                .date(localDate.toString())
                .time(localTime.toString())
                .room(savedInterview.getRoom())
                .address(savedInterview.getAddress())
                .linkMeeting(savedInterview.getLinkMeeting())
                .round(savedInterview.getRound())
                .description(savedInterview.getDescription())
                .status(interview.getStatus())
                .type(savedInterview.getType())
                .jobApply(savedInterview.getJobApply())
                .candidateName(savedInterview.getCandidate().getName())
                .build();

        List<String> empName = new ArrayList<>();
        for (InterviewEmployee interviewEmp : listInterviewEmployees) {
            empName.add(interviewEmp.getEmployee().getName());
        }
        response.setEmployeeNames(empName);
        return response;
    }

    @Override
    public ResponseWithTotalPage<InterviewResponse> getInterviewByCandidateID(int candidateId, int pageNo,
            int pageSize) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Interview> interviews = interviewRepository.findInterviewByCandidateId(candidate.getId(), pageable);
        ResponseWithTotalPage<InterviewResponse> listResponse = new ResponseWithTotalPage<>();
        List<InterviewResponse> responseList = new ArrayList<>();
        if (interviews.hasContent()) {
            for (Interview interview : interviews) {
                DateTimeFormatter timeDislayFormatter = DateTimeFormatter.ofPattern("HH:mm");
                String timeDislay = interview.getDate().toLocalDateTime().toLocalTime().format(timeDislayFormatter);
                InterviewResponse response = InterviewResponse.builder()
                        .id(interview.getId())
                        .subject(interview.getSubject())
                        .purpose(interview.getPurpose())
                        .date(Date.valueOf(interview.getDate().toLocalDateTime().toLocalDate()).toString())
                        .time(timeDislay)
                        .room(interview.getRoom())
                        .address(interview.getAddress())
                        .linkMeeting(interview.getLinkMeeting())
                        .round(interview.getRound())
                        .description(interview.getDescription())
                        .candidateConfirm(interview.getCandidateConfirm())
                        .status(interview.getStatus())
                        .type(interview.getType())
                        .jobApply(interview.getJobApply())
                        .candidateName(interview.getCandidate().getName())
                        .build();

                List<String> empName = new ArrayList<>();
                for (InterviewEmployee interEmp : interview.getInterviewEmployees()) {
                    empName.add(interEmp.getEmployee().getName());
                }
                response.setEmployeeNames(empName);
                responseList.add(response);
            }
        }
        listResponse.setResponseList(responseList);
        listResponse.setTotalPage(interviews.getTotalPages());

        return listResponse;
    }

    @Override
    public ResponseWithTotalPage<InterviewResponse> getInterviewByEmployeeID(int employeeId, int pageNo, int pageSize) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Interview> interviewList = interviewRepository.findInterviewByEmployeeId(employee.getId(), pageable);

        List<String> empName = new ArrayList<>();
        ResponseWithTotalPage<InterviewResponse> listResponse = new ResponseWithTotalPage<>();
        List<InterviewResponse> responseList = new ArrayList<>();
        if (interviewList.hasContent()) {
            for (Interview interview : interviewList) {
                if (empName.size() != 0) {
                    empName.clear();
                }
                for (InterviewEmployee interviewEmp : interview.getInterviewEmployees()) {
                    empName.add(interviewEmp.getEmployee().getName());
                }
                String interviewEmployeeStatus = interviewEmployeeRepository
                        .findByInterviewAndEmployeeStatus(interview.getId(), employeeId);
                DateTimeFormatter timeDislayFormatter = DateTimeFormatter.ofPattern("HH:mm");
                String timeDislay = interview.getDate().toLocalDateTime().toLocalTime().format(timeDislayFormatter);
                InterviewResponse response = InterviewResponse.builder()
                        .id(interview.getId())
                        .subject(interview.getSubject())
                        .purpose(interview.getPurpose())
                        .date(Date.valueOf(interview.getDate().toLocalDateTime().toLocalDate()).toString())
                        .time(timeDislay)
                        .room(interview.getRoom())
                        .address(interview.getAddress())
                        .linkMeeting(interview.getLinkMeeting())
                        .round(interview.getRound())
                        .employeeConfirm(interviewEmployeeStatus)
                        .candidateConfirm(interview.getCandidateConfirm())
                        .description(interview.getDescription())
                        .status(interview.getStatus())
                        .type(interview.getType())
                        .jobApply(interview.getJobApply())
                        .candidateName(interview.getCandidate().getName())
                        .employeeNames(empName)
                        .build();
                responseList.add(response);

            }

        }
        listResponse.setTotalPage(interviewList.getTotalPages());
        listResponse.setResponseList(responseList);
        return listResponse;

    }

    @Override
    public ResponseWithTotalPage<InterviewResponse> getAllInterview(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Interview> interviews = interviewRepository.findAll(pageable);
        List<InterviewResponse> list = new ArrayList<InterviewResponse>();
        ResponseWithTotalPage<InterviewResponse> result = new ResponseWithTotalPage<>();
        List<String> empName = new ArrayList<>();
        if (interviews.hasContent()) {
            for (Interview interview : interviews.getContent()) {
                if (empName.size() != 0) {
                    empName.clear();
                }
                for (InterviewEmployee interviewEmp : interview.getInterviewEmployees()) {
                    empName.add(interviewEmp.getEmployee().getName());
                }
                DateTimeFormatter timeDislayFormatter = DateTimeFormatter.ofPattern("HH:mm");
                String timeDislay = interview.getDate().toLocalDateTime().toLocalTime().format(timeDislayFormatter);
                InterviewResponse interviewResponse = InterviewResponse.builder()
                        .id(interview.getId())
                        .subject(interview.getSubject())
                        .purpose(interview.getPurpose())
                        .date(Date.valueOf(interview.getDate().toLocalDateTime().toLocalDate()).toString())
                        .time(timeDislay)
                        .room(interview.getRoom())
                        .address(interview.getAddress())
                        .linkMeeting(interview.getLinkMeeting())
                        .round(interview.getRound())
                        .candidateConfirm(interview.getCandidateConfirm())
                        .description(interview.getDescription())
                        .status(interview.getStatus())
                        .type(interview.getType())
                        .jobApply(interview.getJobApply())
                        .candidateName(interview.getCandidate().getName())
                        .employeeNames(empName)
                        .build();

                list.add(interviewResponse);
            }
            result.setResponseList(list);
            result.setTotalPage(interviews.getTotalPages());
        } else
            throw new ListEmptyException(InterviewErrorMessage.LIST_EMPTY_EXCEPTION);

        return result;
    }

    @Override
    public InterviewResponse getInterviewByID(int id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));
        List<String> empName = new ArrayList<>();
        for (InterviewEmployee interviewEmp : interview.getInterviewEmployees()) {
            empName.add(interviewEmp.getEmployee().getName());
        }
        DateTimeFormatter timeDislayFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String timeDislay = interview.getDate().toLocalDateTime().toLocalTime().format(timeDislayFormatter);
        InterviewResponse interviewResponse = InterviewResponse.builder()
                .id(interview.getId())
                .subject(interview.getSubject())
                .purpose(interview.getPurpose())
                .date(Date.valueOf(interview.getDate().toLocalDateTime().toLocalDate()).toString())
                .time(timeDislay)
                .room(interview.getRoom())
                .address(interview.getAddress())
                .linkMeeting(interview.getLinkMeeting())
                .round(interview.getRound())
                .description(interview.getDescription())
                .candidateConfirm(interview.getCandidateConfirm())
                .status(interview.getStatus())
                .type(interview.getType())
                .jobApply(interview.getJobApply())
                .candidateName(interview.getCandidate().getName())
                .employeeNames(empName)
                .build();
        return interviewResponse;
    }

    @Override
    public InterviewResponse updateInterview(int id, InterviewUpdateDTO interviewUpdateDTO) throws MessagingException {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));
        Candidate candidate = candidateRepository.findById(interviewUpdateDTO.getCandidateId())
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));
        List<Employee> employeeList = new ArrayList<>();
        for (Integer employeeId : interviewUpdateDTO.getEmployeeIds()) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
            employeeList.add(employee);
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate localDate = LocalDate.parse(interviewUpdateDTO.getDate(), dateFormater);
        LocalTime localTime = LocalTime.parse(interviewUpdateDTO.getTime(), timeFormatter);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        String dateInput = localDateTime.format(dateTimeFormatter);
        Timestamp dateInterview = Timestamp.valueOf(dateInput);

        JobApply jobApply = jobApplyRepository.findById(interviewUpdateDTO.getJobApplyId())
                .orElseThrow(() -> new NotFoundException(JobApplyErrorMessage.JOB_APPLY_NOT_FOUND));
        interview.setSubject(interviewUpdateDTO.getSubject());
        interview.setPurpose(interviewUpdateDTO.getPurpose());
        interview.setDate(dateInterview);
        interview.setAddress(interviewUpdateDTO.getAddress());
        interview.setRoom(interviewUpdateDTO.getRoom());
        interview.setLinkMeeting(interviewUpdateDTO.getLinkMeeting());
        interview.setRound(interviewUpdateDTO.getRound());
        interview.setType(interviewUpdateDTO.getType());
        interview.setDescription(interviewUpdateDTO.getDescription());
        interview.setJobApply(jobApply);
        interview.setCandidate(candidate);

        List<InterviewEmployee> interviewEmployeeList = new ArrayList<>();
        for (InterviewEmployee interviewEmployee : interview.getInterviewEmployees()) {
            interviewEmployeeList.add(interviewEmployee);
        }
        interview.setInterviewEmployees(interviewEmployeeList);
        Interview savedInterview = interviewRepository.save(interview);
        interviewEmployeeRepository.deleteInterviewEmployeeByInterviewId(savedInterview.getId());

        for (Employee emp : employeeList) {
            InterviewEmployee interviewEmployee = InterviewEmployee.builder()
                    .employee(emp)
                    .interview(savedInterview)
                    .build();
            interviewEmployeeRepository.save(interviewEmployee);
        }

        SendNotificationDTO sendNotificationDTO = SendNotificationDTO.builder()
                .link(savedInterview.getLinkMeeting())
                .room(savedInterview.getRoom())
                .address(savedInterview.getAddress())
                .date(savedInterview.getDate())
                .candidate(candidate)
                .IntervieweeID(interviewUpdateDTO.getEmployeeIds())
                .interview(savedInterview)
                .build();
        notificationService.sendNotificationForInterview(sendNotificationDTO);

        List<String> empName = new ArrayList<>();
        for (InterviewEmployee interEmp : savedInterview.getInterviewEmployees()) {
            empName.add(interEmp.getEmployee().getName());
        }
        InterviewResponse interviewResponse = InterviewResponse.builder()
                .id(savedInterview.getId())
                .subject(savedInterview.getSubject())
                .purpose(savedInterview.getPurpose())
                .date(Date.valueOf(savedInterview.getDate().toLocalDateTime().toLocalDate()).toString())
                .time(localTime.toString())
                .address(savedInterview.getAddress())
                .room(savedInterview.getRoom())
                .linkMeeting(savedInterview.getLinkMeeting())
                .round(savedInterview.getRound())
                .description(savedInterview.getDescription())
                .status(savedInterview.getStatus())
                .type(savedInterview.getType())
                .jobApply(savedInterview.getJobApply())
                .candidateName(savedInterview.getCandidate().getName())
                .employeeNames(empName)
                .build();
        return interviewResponse;
    }

    @Override
    public void closeInterview(int id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));
        interview.setStatus(InterviewRequestStatus.DONE);
        interviewRepository.save(interview);
    }

    @Override
    public void cancelInterview(CancelInterviewDTO cancelInterviewDTO) throws MessagingException {
        Interview interview = interviewRepository.findById(cancelInterviewDTO.getInterviewId())
                .orElseThrow(() -> new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));

        interview.setStatus(InterviewRequestStatus.CANCELED);
        interviewRepository.save(interview);
        List<Integer> candidateIDs = new ArrayList<>();
        candidateIDs.add(interview.getCandidate().getId());
        List<Integer> employeeIDs = new ArrayList<>();
        for (InterviewEmployee employee : interview.getInterviewEmployees()) {
            employeeIDs.add(employee.getEmployee().getId());
        }
        String dateFormated = interview.getDate().toString().substring(0, 16);
        String message = "Lịch phỏng vấn vào ngày " + dateFormated + " đã huỷ\n"
                + "Lý do: " + cancelInterviewDTO.getReason() + "\n" + "Xin thứ lỗi.";
        NotificationCreateDTO notificationCreateDTO = NotificationCreateDTO.builder()
                .title("Huỷ lịch phỏng vấn")
                .content(message)
                .candidateIDs(candidateIDs)
                .employeeIDs(employeeIDs)
                .build();
        notificationService.createNotification(notificationCreateDTO);
    }

    @Override
    public ResponseWithTotalPage<InterviewResponse> getInterviewByDepartment(int departmentId, int pageNo,
            int pageSize) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException(DepartmentErrorMessage.DEPARTMENT_NOT_FOUND_EXCEPTION));

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        Page<Interview> interviews = interviewRepository.findInterviewByDepartrment(department.getId(), pageable);
        ResponseWithTotalPage<InterviewResponse> listResponse = new ResponseWithTotalPage<>();
        List<InterviewResponse> responseList = new ArrayList<>();
        if (interviews.hasContent()) {
            for (Interview interview : interviews) {
                DateTimeFormatter timeDislayFormatter = DateTimeFormatter.ofPattern("HH:mm");
                String timeDislay = interview.getDate().toLocalDateTime().toLocalTime().format(timeDislayFormatter);
                InterviewResponse response = InterviewResponse.builder()
                        .id(interview.getId())
                        .subject(interview.getSubject())
                        .purpose(interview.getPurpose())
                        .date(Date.valueOf(interview.getDate().toLocalDateTime().toLocalDate()).toString())
                        .time(timeDislay)
                        .room(interview.getRoom())
                        .address(interview.getAddress())
                        .linkMeeting(interview.getLinkMeeting())
                        .round(interview.getRound())
                        .description(interview.getDescription())
                        .candidateConfirm(interview.getCandidateConfirm())
                        .status(interview.getStatus())
                        .type(interview.getType())
                        .jobApply(interview.getJobApply())
                        .candidateName(interview.getCandidate().getName())
                        .build();

                List<String> empName = new ArrayList<>();
                for (InterviewEmployee interEmp : interview.getInterviewEmployees()) {
                    empName.add(interEmp.getEmployee().getName());
                }
                response.setEmployeeNames(empName);
                responseList.add(response);
            }
        }
        listResponse.setResponseList(responseList);
        listResponse.setTotalPage(interviews.getTotalPages());

        return listResponse;
    }

    @Override
    public void confirmJoinInterviewByEmployee(int idInterview, int idEmployee) throws MessagingException {
        Interview interview = interviewRepository.findById(idInterview)
                .orElseThrow(() -> new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));
        Employee employee = employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
        InterviewEmployee interviewEmployee = interviewEmployeeRepository.findByInterviewAndEmployee(interview.getId(),
                employee.getId());
        if (interviewEmployee == null) {
            throw new NotValidException(InterviewErrorMessage.INTERVIEW_CONFIRM_NOT_VALID);
        }
        interviewEmployee.setConfirmStatus(InterviewEmployeeRequestStatus.ACCEPTABLE);
        interviewEmployeeRepository.save(interviewEmployee);
        // Check if all emp accept -> interview to approved
        List<InterviewEmployee> interviewEmployeeList = interviewEmployeeRepository.findByInterviewId(idInterview);

        boolean checkAllConfirm = true;
        for (InterviewEmployee interEmp : interviewEmployeeList) {
            if (interEmp.getConfirmStatus() == null ||
                    !interEmp.getConfirmStatus().equals(InterviewEmployeeRequestStatus.ACCEPTABLE)) {
                checkAllConfirm = false;
            }
        }
        if (!interview.getCandidateConfirm().equals(CandidateStatus.INTERVIEW_ACCEPTABLE)) {
            checkAllConfirm = false;
        }
        if (checkAllConfirm) {
            interview.setStatus(InterviewRequestStatus.APPROVED);
            interviewRepository.save(interview);
        }

    }

    @Override
    public void confirmInterviewByManager(int idInterview) throws MessagingException {
        Interview interview = interviewRepository.findById(idInterview)
                .orElseThrow(() -> new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));
        interview.setStatus(InterviewRequestStatus.APPROVED);
        interviewRepository.save(interview);
    }

    @Override
    public void confirmJoinInterviewByCandidate(int idInterview, int idCandidate) {
        Interview interview = interviewRepository.findById(idInterview)
                .orElseThrow(() -> new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));
        Candidate candidate = candidateRepository.findById(idCandidate)
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));
        if (!interview.getCandidate().equals(candidate)) {
            throw new NotValidException(InterviewErrorMessage.INTERVIEW_CONFIRM_NOT_VALID);
        }

        List<InterviewEmployee> interviewEmployeeList = interviewEmployeeRepository.findByInterviewId(idInterview);
        boolean checkAllConfirm = true;
        for (InterviewEmployee interEmp : interviewEmployeeList) {
            if (interEmp.getConfirmStatus() == null
                    || !interEmp.getConfirmStatus().equals(InterviewEmployeeRequestStatus.ACCEPTABLE)) {
                checkAllConfirm = false;
            }
        }
        if (checkAllConfirm) {
            interview.setStatus(InterviewRequestStatus.APPROVED);
            interviewRepository.save(interview);
        }
        interview.setCandidateConfirm(CandidateStatus.INTERVIEW_ACCEPTABLE);
        interviewRepository.save(interview);
    }

    @Override
    public void rejectJoinInterviewByEmployee(int idInterview, int idEmployee) {
        Interview interview = interviewRepository.findById(idInterview)
                .orElseThrow(() -> new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));
        Employee employee = employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
        InterviewEmployee interviewEmployee = interviewEmployeeRepository.findByInterviewAndEmployee(interview.getId(),
                employee.getId());
        if (interviewEmployee == null) {
            throw new NotValidException(InterviewErrorMessage.INTERVIEW_REJECT_NOT_VALID);
        } else if (interviewEmployee.getConfirmStatus() != null &&
                interviewEmployee.getConfirmStatus().equals(InterviewEmployeeRequestStatus.REJECTED)) {
            throw new NotValidException(InterviewErrorMessage.INTERVIEW_WAS_REJECT_NOT_VALID);
        }
        interviewEmployee.setConfirmStatus(InterviewEmployeeRequestStatus.REJECTED);
        interviewEmployeeRepository.save(interviewEmployee);
    }

    @Override
    public void rejectJoinInterviewByCandidate(int idInterview, int idCandidate) throws MessagingException {
        Interview interview = interviewRepository.findById(idInterview)
                .orElseThrow(() -> new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));
        candidateRepository.findById(idCandidate)
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));
        if (interview.getCandidate().getId() != idCandidate) {
            throw new NotValidException(InterviewErrorMessage.INTERVIEW_REJECT_NOT_VALID);
        } else if (interview.getCandidateConfirm() != null &&
                interview.getCandidateConfirm().equals(CandidateStatus.INTERVIEW_REJECTED)) {
            throw new NotValidException(InterviewErrorMessage.INTERVIEW_WAS_REJECT_NOT_VALID);
        }
        interview.setStatus(InterviewRequestStatus.CANCELED);
        interview.setCandidateConfirm(CandidateStatus.INTERVIEW_REJECTED);
        interviewRepository.save(interview);
        List<Integer> candidateIDs = new ArrayList<>();
        candidateIDs.add(interview.getCandidate().getId());
        List<Integer> employeeIDs = new ArrayList<>();
        for (InterviewEmployee employee : interview.getInterviewEmployees()) {
            employeeIDs.add(employee.getEmployee().getId());
        }
        String dateFormated = interview.getDate().toString().substring(0, 16);
        String message = "Lịch phỏng vấn vào ngày " + dateFormated + " đã huỷ\n"
                + "Lý do: Ứng viên từ chối tham gia\n" + "Xin thứ lỗi.";
        NotificationCreateDTO notificationCreateDTO = NotificationCreateDTO.builder()
                .title("Huỷ lịch phỏng vấn")
                .content(message)
                .candidateIDs(candidateIDs)
                .employeeIDs(employeeIDs)
                .build();
        notificationService.createNotification(notificationCreateDTO);
    }

    @Override
    public List<InterviewResponse> searchInterview(String candidateName, String type, String status, String date,
            String round) {
        List<InterviewResponse> result = new ArrayList<InterviewResponse>();
        List<Interview> list = interviewRepository.searchInterview(candidateName, type, status, date, round);
        if (list.size() > 0) {
            for (Interview interview : list) {
                InterviewResponse response = modelMapper.map(interview, InterviewResponse.class);
                result.add(response);
            }
        } else
            throw new ListEmptyException(InterviewErrorMessage.LIST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public List<NameAndStatusResponse> getNameAndStatusByInterviewId(int interviewId) {
        List<Tuple> list = interviewEmployeeRepository.getNameAndConfirmStatusByInterviewId(interviewId);
        if (list.size() <= 0) {
            throw new NotFoundException(InterviewErrorMessage.INTERVIEW_DOES_NOT_ANY_APPLY);
        }
        List<NameAndStatusResponse> responseList = new ArrayList<>();
        for (Tuple tuple : list) {
            NameAndStatusResponse nameAndStatusResponse = NameAndStatusResponse.builder()
                    .name(tuple.get("name").toString())
                    .status(tuple.get("status").toString())
                    .build();
            responseList.add(nameAndStatusResponse);
        }
        return responseList;
    }

    @Override
    public ResponseWithTotalPage<InterviewResponse> getAcceptableByEmployee(int employeeId, int pageNo,
            int pageSize) {
        ResponseWithTotalPage<InterviewResponse> result = new ResponseWithTotalPage<>();
        List<InterviewResponse> listResponse = new ArrayList<>();
        pageNo *= pageSize;
        List<Interview> listInterviews = interviewRepository.getAcceptableInterviewByEmployee(employeeId, pageSize,
                pageNo);
        if (listInterviews.size() > 0) {
            for (Interview interview : listInterviews) {
                DateTimeFormatter timeDislayFormatter = DateTimeFormatter.ofPattern("HH:mm");
                String timeDislay = interview.getDate().toLocalDateTime().toLocalTime().format(timeDislayFormatter);
                InterviewResponse response = InterviewResponse.builder()
                        .id(interview.getId())
                        .subject(interview.getSubject())
                        .purpose(interview.getPurpose())
                        .date(Date.valueOf(interview.getDate().toLocalDateTime().toLocalDate()).toString())
                        .time(timeDislay)
                        .room(interview.getRoom())
                        .address(interview.getAddress())
                        .linkMeeting(interview.getLinkMeeting())
                        .round(interview.getRound())
                        .description(interview.getDescription())
                        .candidateConfirm(interview.getCandidateConfirm())
                        .status(interview.getStatus())
                        .type(interview.getType())
                        .jobApply(interview.getJobApply())
                        .candidateName(interview.getCandidate().getName())
                        .build();
                List<String> empName = new ArrayList<>();
                for (InterviewEmployee interEmp : interview.getInterviewEmployees()) {
                    empName.add(interEmp.getEmployee().getName());
                }
                response.setEmployeeNames(empName);
                listResponse.add(response);
            }
            int totalElements = interviewRepository.getTotalAcceptableInterviewByEmployee(employeeId);
            int totalPage = (totalElements / pageSize) + ((totalElements % pageSize == 0) ? 0 : 1);
            result.setTotalPage(totalPage);
            result.setResponseList(listResponse);
        } else {
            throw new ListEmptyException(InterviewErrorMessage.LIST_EMPTY_EXCEPTION);
        }
        return result;
    }

    @Override
    public ResponseWithTotalPage<InterviewResponse> getAcceptableByDepartment(int departmentId, int pageNo,
            int pageSize) {
        ResponseWithTotalPage<InterviewResponse> result = new ResponseWithTotalPage<>();
        List<InterviewResponse> listResponse = new ArrayList<>();
        pageNo *= pageSize;
        List<Interview> listInterviews = interviewRepository.getAcceptableInterviewByDepartment(departmentId, pageSize,
                pageNo);
        if (listInterviews.size() > 0) {
            for (Interview interview : listInterviews) {
                DateTimeFormatter timeDislayFormatter = DateTimeFormatter.ofPattern("HH:mm");
                String timeDislay = interview.getDate().toLocalDateTime().toLocalTime().format(timeDislayFormatter);
                InterviewResponse response = InterviewResponse.builder()
                        .id(interview.getId())
                        .subject(interview.getSubject())
                        .purpose(interview.getPurpose())
                        .date(Date.valueOf(interview.getDate().toLocalDateTime().toLocalDate()).toString())
                        .time(timeDislay)
                        .room(interview.getRoom())
                        .address(interview.getAddress())
                        .linkMeeting(interview.getLinkMeeting())
                        .round(interview.getRound())
                        .description(interview.getDescription())
                        .candidateConfirm(interview.getCandidateConfirm())
                        .status(interview.getStatus())
                        .type(interview.getType())
                        .jobApply(interview.getJobApply())
                        .candidateName(interview.getCandidate().getName())
                        .build();
                List<String> empName = new ArrayList<>();
                for (InterviewEmployee interEmp : interview.getInterviewEmployees()) {
                    empName.add(interEmp.getEmployee().getName());
                }
                response.setEmployeeNames(empName);
                listResponse.add(response);
            }
            int totalElements = interviewRepository.getTotalAcceptableInterviewByDepartment(departmentId);
            int totalPage = (totalElements / pageSize) + ((totalElements % pageSize == 0) ? 0 : 1);
            result.setTotalPage(totalPage);
            result.setResponseList(listResponse);
        } else {
            throw new ListEmptyException(InterviewErrorMessage.LIST_EMPTY_EXCEPTION);
        }
        return result;
    }

}
