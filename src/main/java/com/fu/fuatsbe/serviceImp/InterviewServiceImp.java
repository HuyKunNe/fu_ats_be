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

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.CancelInterviewDTO;
import com.fu.fuatsbe.DTO.InterviewCreateDTO;
import com.fu.fuatsbe.DTO.NotificationCreateDTO;
import com.fu.fuatsbe.DTO.SendInviteInterviewEmployee;
import com.fu.fuatsbe.DTO.SendNotificationDTO;
import com.fu.fuatsbe.constant.candidate.CandidateErrorMessage;
import com.fu.fuatsbe.constant.candidate.CandidateStatus;
import com.fu.fuatsbe.constant.department.DepartmentErrorMessage;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.interview.InterviewErrorMessage;
import com.fu.fuatsbe.constant.interview.InterviewRequestStatus;
import com.fu.fuatsbe.constant.interview_employee.InterviewEmployeeRequestStatus;
import com.fu.fuatsbe.constant.job_apply.JobApplyErrorMessage;
import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Department;
import com.fu.fuatsbe.entity.EmailSchedule;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.Interview;
import com.fu.fuatsbe.entity.InterviewEmployee;
import com.fu.fuatsbe.entity.JobApply;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.exceptions.NotValidException;
import com.fu.fuatsbe.exceptions.PermissionException;
import com.fu.fuatsbe.repository.CandidateRepository;
import com.fu.fuatsbe.repository.DepartmentRepository;
import com.fu.fuatsbe.repository.EmailScheduleRepository;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.InterviewEmployeeRepository;
import com.fu.fuatsbe.repository.InterviewRepository;
import com.fu.fuatsbe.repository.JobApplyRepository;
import com.fu.fuatsbe.repository.NotificationRepository;
import com.fu.fuatsbe.repository.PositionRepository;
import com.fu.fuatsbe.response.InterviewResponse;
import com.fu.fuatsbe.response.NameAndStatusResponse;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.InterviewService;
import com.fu.fuatsbe.service.NotificationService;

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
    private final EmailScheduleRepository emailScheduleRepository;
    private final PositionRepository positionRepository;

    private final InterviewEmployeeRepository interviewEmployeeRepository;

    private final ModelMapper modelMapper;

    @Override
    public void createInterview(InterviewCreateDTO interviewCreateDTO) throws MessagingException {
        List<Employee> employeeList = new ArrayList<>();
        List<Candidate> candidateList = new ArrayList<>();
        List<Integer> intervieweeIdList = new ArrayList<>();
        for (Integer employeeId : interviewCreateDTO.getEmployeeId()) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
            employeeList.add(employee);
            intervieweeIdList.add(employeeId);
        }
        for (Integer candidateId : interviewCreateDTO.getCandidateId()) {
            Candidate candidate = candidateRepository.findById(candidateId)
                    .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));
            candidateList.add(candidate);
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate localDate = LocalDate.parse(interviewCreateDTO.getDate(), dateFormatter);
        LocalTime localTime = LocalTime.parse(interviewCreateDTO.getTime(), timeFormatter);

        LocalDate presentDate = LocalDate.parse(LocalDate.now().toString(), dateFormatter);
        String jobName = positionRepository
                .getNamePositionByRecruitmentRequest(interviewCreateDTO.getRecruitmentRequestId());

        if (localDate.isBefore(presentDate)) {
            throw new PermissionException(InterviewErrorMessage.DATE_NOT_VALID);
        }
        int loopTimes = 0;
        LocalTime newLocalTime = null;
        for (Candidate candidate : candidateList) {
            newLocalTime = localTime.plusMinutes(interviewCreateDTO.getDuration() * loopTimes);
            LocalDateTime localDateTime = LocalDateTime.of(localDate, newLocalTime);
            String dateInput = localDateTime.format(dateTimeFormatter);
            Timestamp dateInterview = Timestamp.valueOf(dateInput);
            JobApply jobApply = jobApplyRepository.getJobAppliesByRecruitmentAndCandidate(
                    interviewCreateDTO.getRecruitmentRequestId(), candidate.getId())
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
                    .duration(interviewCreateDTO.getDuration())
                    .candidate(candidate)
                    .IntervieweeID(intervieweeIdList)
                    .interview(savedInterview)
                    .jobName(jobName)
                    .build();
            notificationService.sendNotificationForInterview(sendNotificationDTO);
            loopTimes++;
        }
        for (Employee emp : employeeList) {
            String time = interviewCreateDTO.getTime();
            if (!interviewCreateDTO.getTime().equals(newLocalTime.toString())) {
                time = interviewCreateDTO.getTime() + "-" + newLocalTime.plusMinutes(interviewCreateDTO.getDuration());
            }
            SendInviteInterviewEmployee sendInviteInterviewEmployee = SendInviteInterviewEmployee.builder()
                    .link(interviewCreateDTO.getLinkMeeting())
                    .address(interviewCreateDTO.getAddress())
                    .room(interviewCreateDTO.getRoom())
                    .jobName(jobName)
                    .date(interviewCreateDTO.getDate())
                    .time(time)
                    .employee(emp)
                    .build();
            notificationService.sendInterviewNotiForEmployee(sendInviteInterviewEmployee);
        }

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
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
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
        String message = "The interview at " + dateFormated + " has canceled\n"
                + "Reason: " + cancelInterviewDTO.getReason() + "\n" + "So sorry for this inconvenience." +
                "\n" +
                " \n" +
                "Thanks & Best Regards,\n" +
                "\n" +
                "--------------------\n" +
                "HR Department | CKHR Consulting\n" +
                "Ground Floor, Rosana Building, 60 Nguyen Dinh Chieu, Da Kao Ward, District 1, HCMC\n" +
                "\n" +
                "Phone: (+8428) 7106 8279 Email: info@ckhrconsulting.vn Website: ckhrconsulting.vn";
        NotificationCreateDTO notificationCreateDTO = NotificationCreateDTO.builder()
                .title("Cancel Interview Schedule")
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
        // if
        // (!interview.getCandidateConfirm().equals(CandidateStatus.INTERVIEW_ACCEPTABLE)
        // &&
        // interview.getCandidateConfirm() == null) {
        // checkAllConfirm = false;
        // }
        if (checkAllConfirm) {
            interview.setStatus(InterviewRequestStatus.APPROVED);
            interviewRepository.save(interview);
            Tuple tuple = notificationRepository.getNotificationByInterview(idInterview);
            Candidate candidate = interview.getCandidate();
            EmailSchedule emailSchedule = EmailSchedule.builder()
                    .email(candidate.getAccount().getEmail())
                    .content(tuple.get("content").toString())
                    .title(tuple.get("subject").toString())
                    .build();
            emailScheduleRepository.save(emailSchedule);
        }

    }

    @Override
    public void confirmInterviewByManager(int idInterview) throws MessagingException {
        Interview interview = interviewRepository.findById(idInterview)
                .orElseThrow(() -> new NotFoundException(InterviewErrorMessage.INTERVIEW_NOT_FOUND));
        interview.setStatus(InterviewRequestStatus.APPROVED);
        interviewRepository.save(interview);
        Tuple tuple = notificationRepository.getNotificationByInterview(idInterview);
        Candidate candidate = interview.getCandidate();
        EmailSchedule emailSchedule = EmailSchedule.builder()
                .email(candidate.getAccount().getEmail())
                .content(tuple.get("content").toString())
                .title(tuple.get("subject").toString())
                .build();
        emailScheduleRepository.save(emailSchedule);
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
        String message = "The interview at " + dateFormated + " is canceled\n"
                + "Reason: Candidate reject to join the interview\n" + "So sorry for this inconvenience." +
                "\n" +
                " \n" +
                "Thanks & Best Regards,\n" +
                "\n" +
                "--------------------\n" +
                "HR Department | CKHR Consulting\n" +
                "Ground Floor, Rosana Building, 60 Nguyen Dinh Chieu, Da Kao Ward, District 1, HCMC\n" +
                "\n" +
                "Phone: (+8428) 7106 8279 Email: info@ckhrconsulting.vn Website: ckhrconsulting.vn";
        NotificationCreateDTO notificationCreateDTO = NotificationCreateDTO.builder()
                .title("Cancel Interview Schedule")
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
