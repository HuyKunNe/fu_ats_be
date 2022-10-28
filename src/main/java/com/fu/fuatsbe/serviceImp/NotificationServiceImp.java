package com.fu.fuatsbe.serviceImp;

import com.fu.fuatsbe.DTO.SendNotificationDTO;
import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.notification.NotificationStatus;
import com.fu.fuatsbe.entity.Candidate;
import com.fu.fuatsbe.entity.Employee;
import com.fu.fuatsbe.entity.Notification;
import com.fu.fuatsbe.exceptions.NotFoundException;
import com.fu.fuatsbe.repository.EmployeeRepository;
import com.fu.fuatsbe.repository.NotificationRepository;
import com.fu.fuatsbe.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImp implements NotificationService {
    private final EmployeeRepository employeeRepository;

    private final NotificationRepository notificationRepository;
    private final JavaMailSender javaMailSender;

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
        String dateFormated = simpleDateFormat.format(sendNotificationDTO.getDate());
        String presentDate = simpleDateFormat.format(Date.valueOf(LocalDate.now()));

        String subject = "Thông báo lịch phỏng vấn";
        String content = "Bạn có 1 buổi phỏng vấn vào lúc " + dateFormated + " \n"
                + "Bạn hãy đến địa chỉ này trước thời gian để tiến hành phỏng vấn \n"
                + "Địa chỉ: " + interviewAddress + ".\n"
                + "Trân trọng.";

        Notification notification = Notification.builder()
                .subject(subject)
                .content(content)
                .createTime(Date.valueOf(presentDate))
                .candidates(listCandidate)
                .employees(listEmployee)
                .interview(sendNotificationDTO.getInterview())
                .isConfirmed(false)
                .status(NotificationStatus.SUCCESSFULL)
                .build();

        notificationRepository.save(notification);

        sendEmail(sendNotificationDTO.getCandidate().getEmail(), subject, content, sendNotificationDTO.getCandidate().getName());
        for (Employee employee : listEmployee) {
            sendEmail(employee.getAccount().getEmail(), subject, content, employee.getName());
        }
    }

    private void sendEmail(String email, String title, String content, String name) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject( title);
        mimeMessageHelper.setText("Thân gửi " + name + ", \n" +content);
        javaMailSender.send(mimeMessage);
    }
}
