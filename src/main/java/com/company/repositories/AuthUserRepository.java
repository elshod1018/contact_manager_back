package com.company.repositories;

import com.company.entities.AuthUser;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
    @Query("select a from AuthUser a where a.email = ?1")
    Optional<AuthUser> findByEmail(String email);


    @Query("select a from AuthUser a where a.id = ?1")
    @NonNull Optional<AuthUser> findById(@NonNull Integer id);
}
