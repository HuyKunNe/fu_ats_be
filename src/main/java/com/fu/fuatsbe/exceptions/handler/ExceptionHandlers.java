package com.fu.fuatsbe.exceptions.handler;

import com.fu.fuatsbe.constant.employee.EmployeeErrorMessage;
import com.fu.fuatsbe.constant.response.ResponseStatusDTO;
import com.fu.fuatsbe.exceptions.*;
import com.fu.fuatsbe.response.ListResponseDTO;
import com.fu.fuatsbe.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlers extends RuntimeException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(value = { UsernameOrPasswordNotFoundException.class, AuthenticationException.class })
    public ResponseEntity<Object> usernameOrPasswordNotFound(AuthenticationException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setMessage(EmployeeErrorMessage.EMAIL_OR_PASSWORD_INCORRECT);
        dto.setStatus(ResponseStatusDTO.FAILURE);
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = EmailExistException.class)
    public ResponseEntity<Object> emailExistException(EmailExistException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setMessage(exception.getMessage());
        dto.setStatus(ResponseStatusDTO.FAILURE);
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setMessage(exception.getMessage());
        dto.setStatus(ResponseStatusDTO.FAILURE);
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = ListEmptyException.class)
    public ResponseEntity<Object> listEmptyException(ListEmptyException exception) {
        ListResponseDTO dto = new ListResponseDTO();
        dto.setMessage(exception.getMessage());
        dto.setStatus(ResponseStatusDTO.FAILURE);
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = ExistException.class)
    public ResponseEntity<Object> existException(ExistException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setMessage(exception.getMessage());
        dto.setStatus(ResponseStatusDTO.FAILURE);
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(value = NotValidException.class)
    public ResponseEntity<Object> notValidException(NotValidException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setMessage(exception.getMessage());
        dto.setStatus(ResponseStatusDTO.FAILURE);
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(value = PermissionException.class)
    public ResponseEntity<Object> permissionException(PermissionException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setMessage(exception.getMessage());
        dto.setStatus(ResponseStatusDTO.FAILURE);
        return ResponseEntity.badRequest().body(dto);
    }
}
