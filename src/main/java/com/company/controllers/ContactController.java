package com.company.controllers;

import com.company.dtos.ResponseDTO;
import com.company.dtos.contact.ContactDTO;
import com.company.entities.Contact;
import com.company.services.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<ResponseDTO<Contact>> create(@RequestBody ContactDTO dto) {
        return ResponseEntity.ok(new ResponseDTO<>(contactService.create(dto), "Contact created successfully"));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<ResponseDTO<List<Contact>>> getAll() {
        log.info("Get all contacts");
        return ResponseEntity.ok(new ResponseDTO<>(contactService.findAll(), "Contacts retrieved successfully"));
    }

    @GetMapping("/{id:.*}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<ResponseDTO<Contact>> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(new ResponseDTO<>(contactService.findById(id), "Contact retrieved successfully"));
    }

    @PutMapping("/update/{id:.*}")
    public ResponseEntity<ResponseDTO<Contact>> update(@PathVariable Integer id, @RequestBody ContactDTO dto) {
        return ResponseEntity.ok(new ResponseDTO<>(contactService.update(id, dto), "Contact updated successfully"));
    }

    @DeleteMapping("/delete/{id:.*}")
    public ResponseEntity<ResponseDTO<Boolean>> delete(@PathVariable Integer id) {
        contactService.delete(id);
        return ResponseEntity.ok(new ResponseDTO<>(true, "Contact deleted successfully"));
    }
}
