package com.company.repositories;

import com.company.entities.Contact;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    @NotNull
    @Query("select c from contacts c where c.deleted = false order by c.name,c.mobile asc ")
    List<Contact> findAll();

    @Query("select c from contacts c where c.mobile = ?1 and c.deleted = false")
    Contact findByMobile(String mobile);
}