package com.company.dtos.authuser;

import lombok.NonNull;

public record TokenRequest(@NonNull String email, @NonNull String password) {
}
