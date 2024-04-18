package ru.teamfour.dao.entity.animal;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamfour.dao.entity.ParentUUIDEntity;
import ru.teamfour.dao.entity.shelter.Shelter;

import java.util.UUID;

/**
 * класс для сущности животное, в котором создается сущность по типу животного
 * имени, возрасту, породе, привычкам, усыновление, id приюта
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "animals")
public class Animal extends ParentUUIDEntity {
    @Column(name = "type_of_animal")
    @Enumerated(EnumType.STRING)
    private TypeAnimal typeAnimal;
    /***
     * кличка
     */
    @Column(name = "name")
    private String name;
    /***
     * возраст
     */
    @Column(name = "age")
    private Double age;
    /***
     * порода
     */
    @Column(name = "breed")
    private String breed;
    /***
     * привычки
     */
    @Column(name = "habits")
    private String habits;
    /***
     * Отметка об усыновлении
     * 1 - усыновлен, 0 - неусыновлен
     */
    @Column(name = "adopted")
    @Enumerated(EnumType.STRING)
    private AdoptionAnimalState adopted;

    /**
     * Приют к которому относится животное
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @Builder
    public Animal(UUID id, TypeAnimal typeAnimal, String name, Double age, String breed, String habits, AdoptionAnimalState adopted, Shelter shelter) {
        super(id);
        this.typeAnimal = typeAnimal;
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.habits = habits;
        this.adopted = adopted;
        this.shelter = shelter;
    }

}
