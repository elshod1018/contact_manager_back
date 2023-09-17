package com.company.controllers;

import com.company.dtos.MessageSendDTO;
import com.company.entities.AuthUser;
import com.company.entities.UserSMS;
import com.company.dtos.authuser.*;
import com.company.rabbitmq.producer.RabbitMQProducer;
import com.company.services.AuthUserService;
import com.company.services.UserSMSService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthUserService authUserService;
    private final UserSMSService userSMSService;
    private final RabbitMQProducer rabbitMQProducer;

    @PreAuthorize("isAnonymous()")
    @PostMapping("/user/register")
    public ResponseEntity<AuthUser> register(@Valid @RequestBody UserCreateDTO dto) throws JsonProcessingException {
        AuthUser authUser = authUserService.create(dto);
        UserSMS smsCode = userSMSService.createSMSCode(authUser);
        rabbitMQProducer.sendMessage(new MessageSendDTO(authUser.getEmail(), smsCode.getCode()));
        return ResponseEntity.ok(authUser);
    }

//    @PreAuthorize("isAnonymous()")
    @PostMapping("/token/   access")
    public ResponseEntity<TokenResponse> generateToken(@RequestBody TokenRequest tokenRequest) {
        TokenResponse tokenResponse = authUserService.generateToken(tokenRequest);
        tokenResponse.setRole(authUserService.findByEmail(tokenRequest.email()).getRole());
        return ResponseEntity.ok(tokenResponse);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/token/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        System.err.println(refreshTokenRequest.refreshToken());
        TokenResponse tokenResponse = authUserService.refreshAccessToken(refreshTokenRequest);
        return ResponseEntity.ok(tokenResponse);
    }


    @GetMapping("/token/validate")
    public Boolean validateToken(@RequestParam(name = "token") String token) {
        try {
            return authUserService.validateToken(token);
        } catch (Exception e) {
            log.error("Error while validating token: {}", e.getMessage());
            return false;
        }
    }

    @PreAuthorize("isAnonymous()")
    @PutMapping("/user/activate")
    public ResponseEntity<AuthUser> activateUser(@RequestBody UserActivationDTO dto) {
        AuthUser authUser = authUserService.activateUser(dto);
        return ResponseEntity.ok(authUser);
    }
}

