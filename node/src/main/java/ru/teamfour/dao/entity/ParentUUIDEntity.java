package ru.teamfour.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Классс родитель для entity. Добавляет id поле типа UUID
 */
@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class ParentUUIDEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected UUID id;

}