package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.DTO.InviteReapplyDTO;
import com.fu.fuatsbe.DTO.NotificationCreateDTO;
import com.fu.fuatsbe.constant.notification.NotificationSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.entity.Notification;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.response.ResponseWithTotalPage;
import com.fu.fuatsbe.service.EmailService;
import com.fu.fuatsbe.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/notification")
@CrossOrigin("*")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final EmailService emailService;

    @PostMapping("/createNotification")
    public ResponseEntity<ResponseDTO> createNotification(@RequestBody NotificationCreateDTO notificationCreateDTO)
            throws MessagingException {
        ResponseDTO response = new ResponseDTO();
        notificationService.createNotification(notificationCreateDTO);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        response.setMessage(NotificationSuccessMessage.CREATE_NOTIFICATION_SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getByCandidate")
    @PreAuthorize(RolePreAuthorize.IS_AUTHENTICATED)
    public ResponseEntity<ResponseDTO> getByCandidate(
            @RequestParam("candidateId") int id,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<Notification> list = notificationService.getAllByCandidate(id, pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(NotificationSuccessMessage.GET_NOTIFICATION_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getByEmployee")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getByEmployee(
            @RequestParam("employeeId") int id,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        ResponseDTO<ResponseWithTotalPage> responseDTO = new ResponseDTO();
        ResponseWithTotalPage<Notification> list = notificationService.getAllByEmployee(id, pageNo, pageSize);
        responseDTO.setData(list);
        responseDTO.setMessage(NotificationSuccessMessage.GET_NOTIFICATION_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
    @PostMapping("/inviteReapply")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> inviteReapplyJob(@RequestBody InviteReapplyDTO inviteReapplyDTO) throws MessagingException {
        ResponseDTO responseDTO = new ResponseDTO();
        emailService.sendEmailToInviteReapply(inviteReapplyDTO);
        responseDTO.setMessage(NotificationSuccessMessage.INVITE_MAIL_SUCCESS);
        responseDTO.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(responseDTO);
    }
}
