package com.company.ex_handlers.handlers;

import com.company.dtos.AppErrorDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ServletException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AppErrorDTO handleServletExceptions(ServletException e, HttpServletRequest request) {
        AppErrorDTO error = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 401);
        log.info("Error: {},{}", request.getRequestURI(), error.getErrorMessage());
        return error;
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AppErrorDTO handleDisabledException(DisabledException e, HttpServletRequest request) {
        AppErrorDTO error = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 403);
        log.info("Error: {},{}", request.getRequestURI(), error.getErrorMessage());
        return error;
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppErrorDTO handleCredentialsExpiredException(CredentialsExpiredException e, HttpServletRequest request) {
        return new AppErrorDTO(request.getRequestURI(), e.getMessage(), 400);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AppErrorDTO handleInsufficientAuthenticationException(InsufficientAuthenticationException e, HttpServletRequest request) {
        return new AppErrorDTO(request.getRequestURI(), e.getMessage(), 403);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AppErrorDTO handleExpiredJwtException(ExpiredJwtException e, HttpServletRequest request) {
        log.error("Error ExpiredJwtException {},{}", request.getRequestURI(), e.getMessage());
        return new AppErrorDTO(request.getRequestURI(), e.getMessage(), 401);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String errorMessage = "Input is not valid";
        Map<String, List<String>> errorBody = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorBody.compute(field, (s, values) -> {
                if (!Objects.isNull(values)) {
                    values.add(message);
                } else {
                    values = new ArrayList<>(Collections.singleton(message));
                }
                return values;
            });
        }
        String errorPath = request.getRequestURI();
        return new AppErrorDTO(errorPath, errorMessage, errorBody, 400);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppErrorDTO handleUnknownExceptions(Exception e, HttpServletRequest request) {
        AppErrorDTO error = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 500);
        log.info("Error: {},{},{}", request.getRequestURI(), error.getErrorMessage(), request.getMethod());
        return error;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AppErrorDTO handleRuntimeExceptions(RuntimeException e, HttpServletRequest request) {
        AppErrorDTO error = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 400);
        log.info("Error: {},{},{}", request.getRequestURI(), error.getErrorMessage(), request.getMethod());
        return error;
    }
}
