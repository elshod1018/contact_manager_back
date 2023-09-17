package com.company.repositories;

import com.company.entities.Group;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    @NotNull
    @Query("select c from groups c where c.deleted = false order by c.name asc ")
    List<Group> findAll();
}