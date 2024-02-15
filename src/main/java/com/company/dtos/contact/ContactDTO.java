package com.company.dtos.contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContactDTO(@NotNull @NotBlank String name,
                         @NotNull @NotBlank String photoUrl,
                         @NotNull @Email @NotBlank String email,
                         @NotNull @NotBlank String mobile,
                         String company,
                         String title,
                         @NotNull Integer groupId) {
}
