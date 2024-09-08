package com.example.vmacademy.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class AuditEntity {

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createAt;

    @UpdateTimestamp
    @Column(name = "last_modified_at")
    private ZonedDateTime lastModifiedAt;

}
