package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.DTO.NotificationCreateDTO;
import com.fu.fuatsbe.constant.notification.NotificationSuccessMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.ResponseDTO;
import com.fu.fuatsbe.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@CrossOrigin("*")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PutMapping("/confirm")
    public ResponseEntity<ResponseDTO> confirmJoinMeeting(@RequestParam int notification_id) {
        ResponseDTO response = new ResponseDTO();
        notificationService.confirmJoinMeeting(notification_id);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        response.setMessage(NotificationSuccessMessage.CONFIRM_JOIN_MEETING_SUCCESS);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/createNotification")
    public ResponseEntity<ResponseDTO> createNotification(@RequestBody NotificationCreateDTO notificationCreateDTO){
        ResponseDTO response = new ResponseDTO();
        notificationService.createNotification(notificationCreateDTO);
        response.setStatus(ResponseStatusDTO.SUCCESS);
        response.setMessage(NotificationSuccessMessage.CREATE_NOTIFICATION_SUCCESS);
        return ResponseEntity.ok().body(response);
    }
}
