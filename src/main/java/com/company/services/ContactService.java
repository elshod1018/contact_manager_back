package com.company.services;

import com.company.config.security.SessionUser;
import com.company.dtos.contact.ContactDTO;
import com.company.entities.Contact;
import com.company.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final SessionUser sessionUser;

    public List<Contact> findAll() {
        Integer id = sessionUser.id();
        return contactRepository.findAll(id);
    }

    public Contact findById(Integer id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id '%s'".formatted(id)));
    }

    public Contact create(ContactDTO dto) {
        String mobile = dto.mobile();
        Contact contact = findByMobile(mobile);
        if (!Objects.isNull(contact)) {
            throw new RuntimeException("Contact with mobile '%s' already exists".formatted(mobile));
        }
        return contactRepository.save(
                Contact.builder()
                        .name(dto.name())
                        .email(dto.email())
                        .title(dto.title())
                        .groupId(dto.groupId())
                        .mobile(mobile)
                        .photoUrl(dto.photoUrl())
                        .company(dto.company())
                        .build()
        );
    }

    private Contact findByMobile(String mobile) {
        return contactRepository.findByMobile(mobile);
    }

    public Contact update(Integer id, ContactDTO dto) {
        Contact contact = findById(id);
        contact.setName(dto.name());
        contact.setEmail(dto.email());
        contact.setMobile(dto.mobile());
        contact.setTitle(dto.title());
        contact.setGroupId(dto.groupId());
        contact.setPhotoUrl(dto.photoUrl());
        contact.setCompany(dto.company());
        return contactRepository.save(contact);
    }

    public void delete(Integer id) {
        Contact contact = findById(id);
        Integer userId = sessionUser.id();
        if (!contact.getCreatedBy().equals(userId)) {
            throw new RuntimeException("Contact not found with id '%s'".formatted(id));
        }
        contact.setDeleted(true);
        contactRepository.save(contact);
    }
}
