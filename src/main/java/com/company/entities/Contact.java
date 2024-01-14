package com.company.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity(name = "contacts")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String photoUrl;

    private String email;

    private String mobile;

    private String company;

    private String title;

    private Integer groupId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;

    private boolean deleted;
}
