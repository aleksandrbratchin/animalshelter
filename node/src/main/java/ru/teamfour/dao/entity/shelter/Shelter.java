package ru.teamfour.dao.entity.shelter;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamfour.dao.entity.ParentUUIDEntity;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shelters")
public class Shelter extends ParentUUIDEntity {

    /**
     * Название приюта
     */
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    /**
     * тип животных в приюте
     */
    @Column(name = "type_of_animal", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeAnimal typeOfAnimal;
    /**
     * О приюте
     */
    @Column(name = "about_shelter", columnDefinition = "TEXT")
    private String aboutShelter;
    /**
     * Адрес
     */
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
    /**
     * График работы приюта
     */
    @Column(name = "work_schedule", columnDefinition = "TEXT")
    private String workSchedule;
    /**
     * Техника безопасности на территории приюта
     */
    @Column(name = "safety_measures", columnDefinition = "TEXT")
    private String safetyMeasures;
    /**
     * данные охраны для оформления пропуска на машину
     */
    @Column(name = "security_data", columnDefinition = "TEXT")
    private String securityData;
    /**
     * животные в приюте
     */
    @OneToMany(
            mappedBy = "shelter",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Animal> animals = new ArrayList<>();

    @Builder
    public Shelter(UUID id, String name, TypeAnimal typeOfAnimal, String aboutShelter, String address, String workSchedule, String safetyMeasures, String securityData, List<Animal> animals) {
        super(id);
        this.name = name;
        this.typeOfAnimal = typeOfAnimal;
        this.aboutShelter = aboutShelter;
        this.address = address;
        this.workSchedule = workSchedule;
        this.safetyMeasures = safetyMeasures;
        this.securityData = securityData;
        this.animals = animals;
    }

    public String getAboutShelter() {
        return aboutShelter;
    }

    public String getAddress() {
        return address;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public String getSafetyMeasures() {
        return safetyMeasures;
    }

    public String getSecurityData() {
        return securityData;
    }


}
