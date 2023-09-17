package com.company.repositories;

import com.company.entities.UserSMS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserSMSRepository extends JpaRepository<UserSMS, Integer> {
    @Query("select u from UserSMS u where u.userId = ?1 and u.toTime > CURRENT_TIMESTAMP and u.expired = false")
    UserSMS findByUserId(Integer userId);
}
