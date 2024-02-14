package com.company.controllers;

import com.company.dtos.ResponseDTO;
import com.company.dtos.authuser.*;
import com.company.entities.AuthUser;
import com.company.entities.UserSMS;
import com.company.services.AuthUserService;
import com.company.services.MailService;
import com.company.services.UserSMSService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthUserService authUserService;
    private final UserSMSService userSMSService;
    private final MailService mailService;

    @PostMapping("/user/register")
    public ResponseEntity<ResponseDTO<AuthUser>> register(@Valid @RequestBody UserCreateDTO dto) {
        AuthUser authUser = authUserService.create(dto);
        UserSMS smsCode = userSMSService.createSMSCode(authUser);
        mailService.sendEmail(authUser.getEmail(), smsCode.getCode());
        return ResponseEntity.ok(new ResponseDTO<>(authUser));
    }

    @PostMapping("/token/access")
    public ResponseEntity<ResponseDTO<TokenResponse>> generateToken(@RequestBody TokenRequest tokenRequest) {
        log.info("Generate token request: {}", tokenRequest.email());
        TokenResponse tokenResponse = authUserService.generateToken(tokenRequest);
        tokenResponse.setRole(authUserService.findByEmail(tokenRequest.email()).getRole());
        return ResponseEntity.ok(new ResponseDTO<>(tokenResponse));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<ResponseDTO<TokenResponse>> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        log.info("Refresh token request: {}", refreshTokenRequest.refreshToken());
        TokenResponse tokenResponse = authUserService.refreshAccessToken(refreshTokenRequest);
        return ResponseEntity.ok(new ResponseDTO<>(tokenResponse));
    }

    @PostMapping("/token/validate")
    public ResponseEntity<ResponseDTO<Boolean>> validateToken(@RequestBody TokenValidateDTO dto) {
        try {
            return ResponseEntity.ok(new ResponseDTO<>(authUserService.validateToken(dto)));
        } catch (Exception e) {
            log.error("Error while validating token: {}", e.getMessage());
            return ResponseEntity.ok(new ResponseDTO<>(false));
        }
    }

    @PutMapping("/user/activate")
    public ResponseEntity<ResponseDTO<AuthUser>> activateUser(@RequestBody UserActivationDTO dto) {
        AuthUser authUser = authUserService.activateUser(dto);
        return ResponseEntity.ok(new ResponseDTO<>(authUser));
    }
}

