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
import com.fu.fuatsbe.repository.CandidateRepository;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.InterviewRepository;
import com.fu.fuatsbe.repository.JobApplyRepository;
import com.fu.fuatsbe.response.InterviewCreateResponse;
import com.fu.fuatsbe.service.InterviewService;
import com.fu.fuatsbe.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;



    @Override
    public InterviewCreateResponse createInterview(InterviewCreateDTO interviewCreateDTO) throws MessagingException {
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
        if(dateInput.isBefore(presentDate)){
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
                .employees(employeeList)
                .jobApply(jobApply)
                .build();
        Interview savedInterview = interviewRepository.save(interview);

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

        InterviewCreateResponse response = modelMapper.map(interview, InterviewCreateResponse.class);
        return response;
    }

    @Override
    public List<Interview> getAllInterview() {
        return null;
    }
}
