package com.company.controllers;

import com.company.entities.Group;
import com.company.services.ContactService;
import com.company.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
@PreAuthorize("isAuthenticated()")
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/all")
    public ResponseEntity<List<Group>> getAll() {
        return ResponseEntity.ok(groupService.findAll());
    }

    @GetMapping("/{id:.*}")
    public ResponseEntity<Group> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(groupService.findById(id));
    }

}
