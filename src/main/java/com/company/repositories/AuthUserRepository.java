package com.company.repositories;

import com.company.entities.AuthUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
    @Query("select a from AuthUser a where a.email = ?1")
    Optional<AuthUser> findByEmail(String email);

    @NotNull
    @Query("select a from AuthUser a where a.id = ?1")
    Optional<AuthUser> findById(@NotNull Integer id);
}
