package com.company.services;

import com.company.entities.AuthUser;
import com.company.entities.UserSMS;
import com.company.repositories.UserSMSRepository;
import com.company.utils.BaseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserSMSService {
    private final UserSMSRepository userSMSRepository;
    private final BaseUtils baseUtils;

    public UserSMS createSMSCode(AuthUser user) {
        Integer userId = user.getId();
        UserSMS userSMS = findByUserId(userId);
        if (!Objects.isNull(userSMS)) {
            return userSMS;
        }
        String smsCode = baseUtils.generateCode();
        return save(userId, smsCode);
    }

    private UserSMS save(Integer userId, String smsCode) {
        UserSMS userSMS = UserSMS.builder()
                .userId(userId)
                .code(smsCode)
                .build();
        return userSMSRepository.save(userSMS);
    }

    public UserSMS findByUserId(Integer userId) {
        return userSMSRepository.findByUserId(userId);
    }

    public UserSMS update(UserSMS userSMS) {
        return userSMSRepository.save(userSMS);
    }

    public void delete(UserSMS userSMS) {
        userSMS.setExpired(true);
        userSMSRepository.save(userSMS);
    }
}
