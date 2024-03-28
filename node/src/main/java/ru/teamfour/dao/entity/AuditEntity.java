package ru.teamfour.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity extends ParentEntity {

    @LastModifiedDate
    protected LocalDateTime date_update;

    @Column(updatable = false)
    @CreatedDate
    protected LocalDateTime date_create;

    public AuditEntity(UUID id) {
        super(id);
    }
}