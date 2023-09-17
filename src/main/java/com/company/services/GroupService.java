package com.company.services;

import com.company.entities.Group;
import com.company.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;


    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Group findById(Integer id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id '%s'".formatted(id)));
    }
}
