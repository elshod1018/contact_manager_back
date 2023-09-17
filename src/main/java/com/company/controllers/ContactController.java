package com.company.controllers;

import com.company.dtos.contact.ContactDTO;
import com.company.entities.Contact;
import com.company.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/create")
    public ResponseEntity<Contact> create(@RequestBody ContactDTO dto) {
        return ResponseEntity.ok(contactService.create(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Contact>> getAll() {
        return ResponseEntity.ok(contactService.findAll());
    }

    @GetMapping("/{id:.*}")
    public ResponseEntity<Contact> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(contactService.findById(id));
    }

    @PutMapping("/update/{id:.*}")
    public ResponseEntity<Contact> update(@PathVariable Integer id, @RequestBody ContactDTO dto) {
        return ResponseEntity.ok(contactService.update(id, dto));
    }

    @DeleteMapping("/delete/{id:.*}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        contactService.delete(id);
        return ResponseEntity.ok(true);
    }
}
