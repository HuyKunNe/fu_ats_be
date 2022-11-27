package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.DTO.NotificationCreateDTO;
import com.fu.fuatsbe.constant.notification.NotificationSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.entity.Notification;
import com.fu.fuatsbe.response.ResponseDTO;
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
    @PreAuthorize(RolePreAuthorize.ROLE_CANDIDATE)
    public ResponseEntity<ResponseDTO> getByCandidate(
            @RequestParam("candidateId") int id) {

        ResponseDTO response = new ResponseDTO();
        List<Notification> list = notificationService.getAllByCandidate(id);
        response.setData(list);
        response.setMessage(NotificationSuccessMessage.GET_NOTIFICATION_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getByEmployee")
    @PreAuthorize(RolePreAuthorize.ROLE_ADMIN_EMPLOYEE)
    public ResponseEntity<ResponseDTO> getByEmployee(
            @RequestParam("employeeId") int id) {

        ResponseDTO response = new ResponseDTO();
        List<Notification> list = notificationService.getAllByEmployee(id);
        response.setData(list);
        response.setMessage(NotificationSuccessMessage.GET_NOTIFICATION_SUCCESS);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        return ResponseEntity.ok().body(response);
    }

}
