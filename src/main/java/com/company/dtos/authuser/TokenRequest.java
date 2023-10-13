package com.company.dtos.authuser;

import lombok.NonNull;
import org.springdoc.core.annotations.ParameterObject;

public record TokenRequest(@NonNull String email, @NonNull String password) {
}
