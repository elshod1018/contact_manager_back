package com.company.dtos.authuser;

import jakarta.validation.constraints.NotBlank;

public record UserActivationDTO(Integer userId,
                                @NotBlank(message = "Code can not be blank") String code) {
}
