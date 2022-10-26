package com.fu.fuatsbe.serviceImp;

import com.fu.fuatsbe.DTO.InterviewCreateDTO;
import com.fu.fuatsbe.DTO.SendNotificationDTO;
import com.fu.fuatsbe.constant.candidate.CandidateErrorMessage;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.interview.InterviewErrorMessage;
import com.fu.fuatsbe.constant.interview.InterviewRequestStatus;
import com.fu.fuatsbe.constant.job_apply.JobApplyErrorMessage;
import com.fu.fuatsbe.entity.*;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.exceptions.PermissionException;
import com.fu.fuatsbe.repository.*;
import com.fu.fuatsbe.response.InterviewResponse;
import com.fu.fuatsbe.service.InterviewService;
import com.fu.fuatsbe.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewServiceImp implements InterviewService {
    private final InterviewRepository interviewRepository;

    private final CandidateRepository candidateRepository;

    private final EmployeeRepository employeeRepository;
    private final JobApplyRepository jobApplyRepository;

    private final NotificationService notificationService;

    private final InterviewEmployeeRepository interviewEmployeeRepository;
    private final ModelMapper modelMapper;


    @Override
    public InterviewResponse createInterview(InterviewCreateDTO interviewCreateDTO) throws MessagingException {
        Candidate candidate = candidateRepository.findById(interviewCreateDTO.getCandidateId()).orElseThrow(()
                -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));

        List<Employee> employeeList = new ArrayList<>();
        List<Integer> intervieweeIdList = new ArrayList<>();


        for (Integer employeeId : interviewCreateDTO.getEmployeeId()) {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                    new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
            employeeList.add(employee);
            intervieweeIdList.add(employeeId);
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateInput = LocalDate.parse(interviewCreateDTO.getDate().toString(), format);
        LocalDate presentDate = LocalDate.parse(LocalDate.now().toString(), format);
        if (dateInput.isBefore(presentDate)) {
            throw new PermissionException(InterviewErrorMessage.DATE_NOT_VALID);
        }
        JobApply jobApply = jobApplyRepository.findById(interviewCreateDTO.getJobApplyId()).orElseThrow(() ->
                new NotFoundException(JobApplyErrorMessage.JOB_APPLY_NOT_FOUND));
        Interview interview = Interview.builder()
                .subject(interviewCreateDTO.getSubject())
                .purpose(interviewCreateDTO.getPurpose())
                .date(Date.valueOf(dateInput))
                .address(interviewCreateDTO.getAddress())
                .room(interviewCreateDTO.getRoom())
                .linkMeeting(interviewCreateDTO.getLinkMeeting())
                .round(interviewCreateDTO.getRound())
                .description(interviewCreateDTO.getDescription())
                .status(InterviewRequestStatus.NEW)
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
                .candidate(candidate)
                .IntervieweeID(intervieweeIdList)
                .interview(savedInterview)
                .build();
        notificationService.sendNotificationForInterview(sendNotificationDTO);

        InterviewResponse response = InterviewResponse.builder()
                .id(savedInterview.getId())
                .subject(savedInterview.getSubject())
                .purpose(savedInterview.getPurpose())
                .date(savedInterview.getDate())
                .room(savedInterview.getRoom())
                .linkMeeting(savedInterview.getLinkMeeting())
                .round(savedInterview.getRound())
                .description(savedInterview.getDescription())
                .jobApply(savedInterview.getJobApply())
                .candidate(savedInterview.getCandidate())
                .build();
        response.setEmployees(employeeRepository.getEmployeeByInterviewId(savedInterview.getId()));
        return response;
    }

    @Override
    public List<InterviewResponse> getInterviewByCandidateID(int candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() ->
                new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));
        List<Interview> interviews = interviewRepository.findInterviewByCandidateId(candidate.getId());
        if (interviews.isEmpty()) {
            throw new NotFoundException(InterviewErrorMessage.INTERVIEW_WITH_CANDIDATE_ID_NOT_FOUND);
        }
        List<InterviewResponse> responseList = new ArrayList<>();
        for (Interview interview : interviews) {
            InterviewResponse response = InterviewResponse.builder()
                    .id(interview.getId())
                    .subject(interview.getSubject())
                    .purpose(interview.getPurpose())
                    .date(interview.getDate())
                    .room(interview.getRoom())
                    .linkMeeting(interview.getLinkMeeting())
                    .round(interview.getRound())
                    .description(interview.getDescription())
                    .jobApply(interview.getJobApply())
                    .candidate(interview.getCandidate())
                    .build();
            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public List<Interview> getAllInterview() {
        return null;
    }

    @Override
    public InterviewResponse updateInterview() {
        return null;
    }

    @Override
    public void closeInterview(int id) {

    }
}
