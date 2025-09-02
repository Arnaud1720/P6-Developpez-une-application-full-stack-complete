//package com.openclassrooms.mddapi.exception;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(EmailAlreadyExistsException.class)
//    public ResponseEntity<?> handleEmailExists(EmailAlreadyExistsException ex) {
//        return ResponseEntity.status(409).body(Map.of("message", ex.getMessage()));
//    }
//}
