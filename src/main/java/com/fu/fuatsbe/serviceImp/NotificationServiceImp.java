package com.fu.fuatsbe.serviceImp;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fu.fuatsbe.DTO.NotificationCreateDTO;
import com.fu.fuatsbe.DTO.SendInviteInterviewEmployee;
import com.fu.fuatsbe.DTO.SendNotificationDTO;
import com.fu.fuatsbe.constant.candidate.CandidateErrorMessage;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.notification.NotificationErrorMessage;
import com.fu.fuatsbe.constant.notification.NotificationStatus;
import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.EmailSchedule;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.Notification;
import com.fu.fuatsbe.exceptions.ListEmptyException;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.repository.AccountRepository;
import com.fu.fuatsbe.repository.CandidateRepository;
import com.fu.fuatsbe.repository.EmailScheduleRepository;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.NotificationRepository;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.EmailScheduleService;
import com.fu.fuatsbe.service.NotificationService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
public class NotificationServiceImp implements NotificationService {
    private final EmployeeRepository employeeRepository;
    private final AccountRepository accountRepository;
    private final EmailScheduleRepository emailScheduleRepository;
    private final EmailScheduleService emailScheduleService;
    private final CandidateRepository candidateRepository;

    private final NotificationRepository notificationRepository;
    private final JavaMailSender javaMailSender;

    @Value("${app.firebase-config}")
    private String firebaseConfig;

    private FirebaseApp firebaseApp;

    @PostConstruct
    private void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(
                            GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig).getInputStream()))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                this.firebaseApp = FirebaseApp.initializeApp(options);
            } else {
                this.firebaseApp = FirebaseApp.getInstance();
            }
        } catch (IOException e) {
            log.error("Create FirebaseApp Error", e);
        }
    }

    @Override
    public void createNotification(NotificationCreateDTO notificationCreateDTO) throws MessagingException {
        List<Candidate> listCandidate = new ArrayList<>();
        List<Employee> listEmployee = new ArrayList<>();
        for (Integer candidateId : notificationCreateDTO.getCandidateIDs()) {
            Candidate candidateInRepo = candidateRepository.findById(candidateId)
                    .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));
            listCandidate.add(candidateInRepo);
        }
        for (Integer employeeId : notificationCreateDTO.getEmployeeIDs()) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
            listEmployee.add(employee);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String presentDate = simpleDateFormat.format(Date.valueOf(LocalDate.now()));
        Notification notification = Notification.builder()
                .subject(notificationCreateDTO.getTitle())
                .content(notificationCreateDTO.getContent())
                .createTime(Date.valueOf(presentDate))
                .candidates(listCandidate)
                .employees(listEmployee)
                .status(NotificationStatus.SUCCESSFULL)
                .build();

        notificationRepository.save(notification);
        for (Employee employee : listEmployee) {
            EmailSchedule emailSchedule = EmailSchedule.builder()
                    .email(employee.getAccount().getEmail())
                    .title(notificationCreateDTO.getTitle())
                    .content(notificationCreateDTO.getContent())
                    .build();
            emailScheduleRepository.save(emailSchedule);
        }

    }

    @Override
    public void sendNotificationForInterview(SendNotificationDTO sendNotificationDTO) throws MessagingException {
        List<Candidate> listCandidate = new ArrayList<>();
        listCandidate.add(sendNotificationDTO.getCandidate());

        for (Integer intervieweeID : sendNotificationDTO.getIntervieweeID()) {
            employeeRepository.findById(intervieweeID)
                    .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
        }
        String interviewAddress = "";
        if (!sendNotificationDTO.getLink().isEmpty()) {
            interviewAddress = "Link meeting: " + sendNotificationDTO.getLink();
        } else {
            interviewAddress = "Room " + sendNotificationDTO.getRoom() + ", " + sendNotificationDTO.getAddress();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String presentDate = simpleDateFormat.format(Date.valueOf(LocalDate.now()));
        String dateFormated = sendNotificationDTO.getDate().toString().substring(0, 10);
        String subject = "Interview Letter";
        String content = "Dear  " + sendNotificationDTO.getCandidate().getName() + "\n"
                + "Thank you so much for your interested in our job opportunities.  As we discussed, I would like to invite you to join our Interview as schedule below\n"
                + "• Position: " + sendNotificationDTO.getJobName() + "\n"
                + "• Time: " + sendNotificationDTO.getDate().toString().substring(11, 16) + " at " + dateFormated + "\n"
                + "• Duration: " + sendNotificationDTO.getDuration() + " minutes\n"
                + "• Address: " + interviewAddress + "\n"
                + " Notes:\n" +
                "• Please well prepare your appearance and background because this interview requires your camera to be turned on(if online interview)\n" +
                "\n" +
                "• Please join the meeting 5 minutes before the above-mentioned time to prepare your network incident (if any)\n" +
                "\n" +
                "• Should you have any further question, please do not hesitate to contact me via email anytime\n" +
                "\n" +
                "• Please confirm to join this interview via the link below.\n" +
                "https://ats-recruitments.tech/#/manage-profile/notification\n" +
                "Yours Sincerely,\n" +
                "\n" +
                " \n" +
                "Thanks & Best Regards, \n" +
                "\n"
                +
                "--------------------\n" +
                "HR Department | CKHR Consulting\n" +
                "Ground Floor, Rosana Building, 60 Nguyen Dinh Chieu, Da Kao Ward, District 1, HCMC\n" +
                "\n" +
                "Phone: (+8428) 7106 8279 Email: info@ckhrconsulting.vn Website: ckhrconsulting.vn";

        Notification notification = Notification.builder()
                .subject(subject)
                .content(content)
                .createTime(Date.valueOf(presentDate))
                .candidates(listCandidate)
                .interview(sendNotificationDTO.getInterview())
                .status(NotificationStatus.SUCCESSFULL)
                .build();

        notificationRepository.save(notification);


    }

    @Override
    public void sendInterviewNotiForEmployee(SendInviteInterviewEmployee sendInviteInterviewEmployee) {
        boolean isExist = employeeRepository.existsById(sendInviteInterviewEmployee.getEmployee().getId());
        if (!isExist) {
            throw new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION);
        }
        String interviewAddress = "";
        if (!sendInviteInterviewEmployee.getLink().isEmpty()) {
            interviewAddress = "Link meeting: " + sendInviteInterviewEmployee.getLink();
        } else {
            interviewAddress = "Room " + sendInviteInterviewEmployee.getRoom() + ", " + sendInviteInterviewEmployee.getAddress();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String presentDate = simpleDateFormat.format(Date.valueOf(LocalDate.now()));
        String subject = "Interview Schedule";
        String content = "Dear  " + sendInviteInterviewEmployee.getEmployee().getName() + "\n"
                + "You have an interview for company to get new employee as schedule below\n"
                + "• Position: " + sendInviteInterviewEmployee.getJobName() + "\n"
                + "• Time: " + sendInviteInterviewEmployee.getTime() + " at " + sendInviteInterviewEmployee.getDate() + "\n"
                + "• Address: " + interviewAddress + "\n"
                +
                "\n" +
                " \n" +
                "Thanks & Best Regards,\n" +
                "\n" +
                "--------------------\n" +
                "HR Department | CKHR Consulting\n" +
                "Ground Floor, Rosana Building, 60 Nguyen Dinh Chieu, Da Kao Ward, District 1, HCMC\n" +
                "\n" +
                "Phone: (+8428) 7106 8279 Email: info@ckhrconsulting.vn Website: ckhrconsulting.vn";
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(sendInviteInterviewEmployee.getEmployee());
        Notification notification = Notification.builder()
                .subject(subject)
                .content(content)
                .createTime(Date.valueOf(presentDate))
                .employees(employeeList)
                .status(NotificationStatus.SUCCESSFULL)
                .build();
        notificationRepository.save(notification);

        EmailSchedule emailSchedule = EmailSchedule.builder()
                .email(sendInviteInterviewEmployee.getEmployee().getAccount().getEmail())
                .content(content)
                .title(subject)
                .build();
        emailScheduleRepository.save(emailSchedule);
    }

    private void sendEmail(String email, String title, String content) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setText(content);
        javaMailSender.send(mimeMessage);
    }

    public void pushNotification(String token, String title, String content) {
        com.google.firebase.messaging.Notification firebaseNoti = com.google.firebase.messaging.Notification
                .builder()
                .setTitle(title)
                .setBody(content)
                .build();
        Message message = Message.builder()
                .setToken(token)
                .setNotification(firebaseNoti)
                .putData("title", title)
                .putData("content", content)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error("Fail to send firebase notification", e);
            throw new RuntimeException(NotificationStatus.UNSUCCESSFULL);
        }

    }

    @Override
    public ResponseWithTotalPage<Notification> getAllByCandidate(int candidateId, int pageNo, int pageSize) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Notification> pageResult = notificationRepository.findNotificationByCandidates(candidate, pageable);
        List<Notification> list = new ArrayList<Notification>();
        ResponseWithTotalPage<Notification> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (Notification notification : pageResult.getContent()) {
                list.add(notification);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(NotificationErrorMessage.LIST_EMPTY_EXCEPTION);
        return result;
    }

    @Override
    public ResponseWithTotalPage<Notification> getAllByEmployee(int employeeId, int pageNo, int pageSize) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Notification> pageResult = notificationRepository.findNotificationByEmployees(employee, pageable);
        List<Notification> list = new ArrayList<Notification>();
        ResponseWithTotalPage<Notification> result = new ResponseWithTotalPage<>();
        if (pageResult.hasContent()) {
            for (Notification notification : pageResult.getContent()) {
                list.add(notification);
            }
            result.setResponseList(list);
            result.setTotalPage(pageResult.getTotalPages());
        } else
            throw new ListEmptyException(NotificationErrorMessage.LIST_EMPTY_EXCEPTION);
        return result;
    }

    //    @Scheduled(cron = "*/60 * * * * *")//1 minute
    @Scheduled(cron = "*/10 * * * * *")//10 second
    public void sendMailAuto() {
        EmailSchedule emailSchedule = emailScheduleService.getFirstMailSchedule();

        try {
            if (emailSchedule != null) {
                String email = emailSchedule.getEmail();
                sendEmail(emailSchedule.getEmail(), emailSchedule.getTitle(), emailSchedule.getContent());
                emailScheduleRepository.delete(emailSchedule);
                String notiToken = accountRepository.getNotiTokenByMail(email);
                if (notiToken != null) {
                    pushNotification(notiToken, emailSchedule.getTitle(), emailSchedule.getContent());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
