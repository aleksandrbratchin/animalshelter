package ru.teamfour.dao.entity.animal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamfour.dao.entity.ParentEntity;

import java.util.UUID;

/**
 *абстрактный класс - родитель для классов животных
 */
@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@Entity
@Table()
public abstract class Animal extends ParentEntity {
    @Column(name = "name")
    String name;
    @Column(name="age")
    Integer age;
    @Column(name="breed")  //порода
    String breed;
    @Column(name="habits")  //привычки
    String habits;
    @Column(name="adopted")  //усыновление
    boolean adopted;
    @Column(name="id_shelter")
    UUID idShelter;
    @Builder
    public Animal(UUID id, String name, Integer age,
                  String breed, String habits,
                  boolean adopted, UUID idShelter) {
        super(id);
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.habits = habits;
        this.adopted = adopted;
        this.idShelter = idShelter;
    }
}
