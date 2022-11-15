package com.fu.fuatsbe.serviceImp;

import com.fu.fuatsbe.DTO.NotificationCreateDTO;
import com.fu.fuatsbe.DTO.SendNotificationDTO;
import com.fu.fuatsbe.constant.candidate.CandidateErrorMessage;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.notification.NotificationErrorMessage;
import com.fu.fuatsbe.constant.notification.NotificationStatus;
import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.Notification;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.repository.CandidateRepository;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.NotificationRepository;
import com.fu.fuatsbe.service.NotificationService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImp implements NotificationService {
    private final EmployeeRepository employeeRepository;
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
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig).getInputStream()))
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
            Candidate candidateInRepo = candidateRepository.findById(candidateId).orElseThrow(() ->
                    new NotFoundException(CandidateErrorMessage.CANDIDATE_NOT_FOUND_EXCEPTION));
            listCandidate.add(candidateInRepo);
        }
        for (Integer employeeId : notificationCreateDTO.getEmployeeIDs()) {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                    new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
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
        for (Candidate candidate : listCandidate) {
            if(candidate.getAccount().getNotificationToken()!=null){
                pushNotification(candidate.getAccount().getNotificationToken(),
                        notificationCreateDTO.getTitle(),
                        notificationCreateDTO.getContent());
            }
            sendEmail(candidate.getEmail(), notificationCreateDTO.getTitle(), notificationCreateDTO.getContent(), candidate.getName());
        }
        for (Employee employee : listEmployee) {
            if(employee.getAccount().getNotificationToken() != null){
                pushNotification(employee.getAccount().getNotificationToken(),
                        notificationCreateDTO.getTitle(),
                        notificationCreateDTO.getContent());
            }
        }


    }

    @Override
    public void sendNotificationForInterview(SendNotificationDTO sendNotificationDTO) throws MessagingException {
        List<Candidate> listCandidate = new ArrayList<>();
        listCandidate.add(sendNotificationDTO.getCandidate());

        List<Employee> listEmployee = new ArrayList<>();

        for (Integer intervieweeID : sendNotificationDTO.getIntervieweeID()) {
            Employee employee = employeeRepository.findById(intervieweeID)
                    .orElseThrow(() -> new NotFoundException(EmployeeErrorMessage.EMPLOYEE_NOT_FOUND_EXCEPTION));
            listEmployee.add(employee);
        }
        String interviewAddress = "";
        if (!sendNotificationDTO.getLink().isEmpty()) {
            interviewAddress = sendNotificationDTO.getLink();
        } else {
            interviewAddress = "Phòng " + sendNotificationDTO.getRoom() + ", " + sendNotificationDTO.getAddress();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String presentDate = simpleDateFormat.format(Date.valueOf(LocalDate.now()));
        String dateFormated = sendNotificationDTO.getDate().toString().substring(0,16);

        String subject = "Thông báo lịch phỏng vấn";
        String content = "Bạn có 1 buổi phỏng vấn vào lúc " + dateFormated+ "\n"
                + "Bạn hãy đến địa chỉ này trước thời gian để tiến hành phỏng vấn\n"
                + "Địa chỉ: " + interviewAddress + "\n"
                + "Trân trọng.";

        Notification notification = Notification.builder()
                .subject(subject)
                .content(content)
                .createTime(Date.valueOf(presentDate))
                .candidates(listCandidate)
                .employees(listEmployee)
                .interview(sendNotificationDTO.getInterview())
                .status(NotificationStatus.SUCCESSFULL)
                .build();

        notificationRepository.save(notification);
        sendEmail(sendNotificationDTO.getCandidate().getEmail(), subject, content, sendNotificationDTO.getCandidate().getName());
        for (Employee employee : listEmployee) {
            sendEmail(employee.getAccount().getEmail(), subject, content, employee.getName());
        }
        for (Candidate candidate : listCandidate) {
            pushNotification(candidate.getAccount().getNotificationToken(),
                    notification.getSubject(),
                    notification.getContent());
        }
        for (Employee employee : listEmployee) {
            pushNotification(employee.getAccount().getNotificationToken(),
                    notification.getSubject(),
                    notification.getContent());
        }
    }

    private void sendEmail(String email, String title, String content, String name) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setText("Thân gửi " + name + ",\n" + content);
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
}
