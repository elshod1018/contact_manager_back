package com.company.dtos.authuser;

import jakarta.validation.constraints.NotBlank;
import org.springdoc.core.annotations.ParameterObject;

public record RefreshTokenRequest(String refreshToken) {
}
