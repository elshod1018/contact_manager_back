package com.company.dtos.contact;

import jakarta.validation.constraints.NotNull;
import lombok.*;

public record ContactDTO(@NotNull String name,
                         @NotNull String photoUrl,
                         @NotNull String email,
                         @NotNull String mobile,
                         String company,
                         String title,
                         @NotNull Integer groupId) {
}
