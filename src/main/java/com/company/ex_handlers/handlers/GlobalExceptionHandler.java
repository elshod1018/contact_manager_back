package com.company.ex_handlers.handlers;

import com.company.dtos.AppErrorDTO;
import com.company.dtos.ResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //
//    @ExceptionHandler({RuntimeException.class})
//    public ResponseEntity<ResponseDTO<Void>> handleRuntimeExceptions(RuntimeException e, HttpServletRequest request) {
//        AppErrorDTO error = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 400);
//        return ResponseEntity.status(500).body(new ResponseDTO<>(error));
//    }
//
    @ExceptionHandler({ServletException.class})
    public ResponseEntity<ResponseDTO<Void>> handleServletExceptions(ServletException e, HttpServletRequest request) {
        AppErrorDTO error = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 401);
        log.info("Error: {},{}", request.getRequestURI(), error.getErrorMessage());
        return ResponseEntity.status(401).body(new ResponseDTO<>(error));
    }

    //
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ResponseDTO<Void>> handleDisabledException(DisabledException e, HttpServletRequest request) {
        AppErrorDTO error = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 403);
        log.info("Error: {},{}", request.getRequestURI(), error.getErrorMessage());
        return ResponseEntity.status(403).body(new ResponseDTO<>(error));
    }

    //
    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<ResponseDTO<Void>> handleCredentialsExpiredException(CredentialsExpiredException e, HttpServletRequest request) {
        AppErrorDTO error = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 400);
        return ResponseEntity.status(400).body(new ResponseDTO<>(error));
    }

    //
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ResponseDTO<Void>> handleInsufficientAuthenticationException(InsufficientAuthenticationException e, HttpServletRequest request) {
        AppErrorDTO error = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 403);
        return ResponseEntity.status(403).body(new ResponseDTO<>(error));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseDTO<Void>> handleExpiredJwtException(ExpiredJwtException e, HttpServletRequest request) {
        log.error("Error ExpiredJwtException {},{}", request.getRequestURI(), e.getMessage());
        AppErrorDTO error = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 401);
        return ResponseEntity.status(401).body(new ResponseDTO<>(error));
    }
//
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ResponseDTO<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
//        String errorMessage = "Input is not valid";
//        Map<String, List<String>> errorBody = new HashMap<>();
//        for (FieldError fieldError : e.getFieldErrors()) {
//            String field = fieldError.getField();
//            String message = fieldError.getDefaultMessage();
//            errorBody.compute(field, (s, values) -> {
//                if (!Objects.isNull(values))
//                    values.add(message);
//                else
//                    values = new ArrayList<>(Collections.singleton(message));
//                return values;
//            });
//        }
//        String errorPath = request.getRequestURI();
//        AppErrorDTO error = new AppErrorDTO(errorPath, errorMessage, errorBody, 400);
//        return ResponseEntity.status(400).body(new ResponseDTO<>(error));
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<Void>> handleUnknownExceptions(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        AppErrorDTO error = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 500);
        log.info("Error: {},{},{}", request.getRequestURI(), error.getErrorMessage(), request.getMethod());
        return ResponseEntity.internalServerError().body(new ResponseDTO<>(error));
    }
}
