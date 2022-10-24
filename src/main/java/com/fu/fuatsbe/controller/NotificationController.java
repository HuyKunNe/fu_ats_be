package com.fu.fuatsbe.controller;

import com.fu.fuatsbe.constant.role.RolePreAuthorize;
import com.fu.fuatsbe.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@CrossOrigin("*")
@RequiredArgsConstructor
public class NotificationController {

//    @PostMapping("/sendNotification")
//    @PreAuthorize(RolePreAuthorize.ROLE_EMPLOYEE)
//    public ResponseEntity<ResponseDTO> sendNotification(){
//    ResponseDTO response = new ResponseDTO();
//
//    return ResponseEntity.ok().body(response);
//    }
}
