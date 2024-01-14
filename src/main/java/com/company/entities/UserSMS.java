package com.company.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserSMS implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false)
    private String code;

    @Column(nullable = false, updatable = false)
    private Integer userId;

    @CreationTimestamp
    private LocalDateTime fromTime;

    @Builder.Default
    private LocalDateTime toTime = LocalDateTime.now().plusMinutes(2);

    @CreatedBy
    private Integer createdBy;

    @LastModifiedBy
    private Integer updatedBy;

    private boolean expired;
}
