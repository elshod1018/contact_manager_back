package com.company.repositories;

import com.company.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query("select c from contacts c where c.deleted = false  and c.createdBy= ?1 order by c.name,c.mobile asc ")
    List<Contact> findAll(Integer userId);

    @Query("select c from contacts c where c.mobile = ?1 and c.deleted = false")
    Contact findByMobile(String mobile);
}