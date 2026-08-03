package com.sweflow.issue.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "issues")
@Getter
@Setter
@NoArgsConstructor
public class IssueEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length=255)
    private String repository;

    @Column(nullable = false, length=500)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name="created_by", nullable = false, length = 255)
    private String createdBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private IssueStatus status;

    @Column(name="created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
