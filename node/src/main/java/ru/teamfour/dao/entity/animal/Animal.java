package ru.teamfour.dao.entity.animal;

import jakarta.persistence.*;
import lombok.*;
import ru.teamfour.dao.entity.ParentUUIDEntity;
import ru.teamfour.dao.entity.shelter.Shelter;

import java.util.UUID;

/**
 *класс для сущности животное, в котором создается сущность по типу животного
 * имени, возрасту, породе, привычкам, усыновление, id приюта
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "animals")
public class Animal extends ParentUUIDEntity {
    @Column(name="type_of_animal")
    @Enumerated(EnumType.STRING)
    private TypeAnimal typeOfAnimal;
    @Column(name = "name")
    private String name;
    @Column(name="age")
    private Double age;
    @Column(name="breed")  //порода
    private String breed;
    @Column(name="habits")  //привычки
    private String habits;
    @Column(name="adopted")  //усыновление
    private boolean adopted;
    /**
     * Приют к которому относится животное
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
    @Builder
    public Animal(UUID id, TypeAnimal typeOfAnimal, String name, Double age, String breed, String habits, boolean adopted, Shelter shelter) {
        super(id);
        this.typeOfAnimal = typeOfAnimal;
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.habits = habits;
        this.adopted = adopted;
        this.shelter = shelter;
    }

    @Override
    public String toString() {
        return
                " кличка - " + name + ", " +
                " возраст - " + age + ", " +
                " порода - " + breed + ", " +
                " особенности поведения - " + habits +
                "\n"
                ;
    }
}


