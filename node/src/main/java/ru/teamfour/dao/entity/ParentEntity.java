package ru.teamfour.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public abstract class ParentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected UUID id;

    public ParentEntity(UUID id) {
        this.id = id;
    }
}