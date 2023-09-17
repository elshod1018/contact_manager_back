package com.company.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@RequiredArgsConstructor
public class BaseUtils {
    private final Random random;

    public String generateCode() {
        return String.valueOf(random.nextInt(100_000, 999_999));
    }
}
