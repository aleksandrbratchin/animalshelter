package ru.teamfour.dao.entity.animal;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * класс для создания объекта - собака
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dogs")
public class Dog extends Animal{
    public Dog(UUID id, String name, Integer age,
               String breed, String habits,
               boolean adopted, UUID idShelter) {
        super(id, name, age, breed, habits, adopted, idShelter);
    }
}
