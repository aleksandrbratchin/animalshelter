package ru.teamfour.dao.entity.shelter;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamfour.dao.entity.ParentUUIDEntity;
import ru.teamfour.dao.entity.animal.Animal;

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
     * О приюте
     */
    @Column(name = "about_shelter")
    private String aboutShelter;
    /**
     * Адрес
     */
    @Column(name = "address")
    private String address;
    /**
     * График работы приюта
     */
    @Column(name = "work_schedule")
    private String workSchedule;
    /**
     * Техника безопасности на территории приюта
     */
    @Column(name = "safety_measures")
    private String safetyMeasures;
    /**
     * данные охраны для оформления пропуска на машину
     */
    @Column(name = "security_data")
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
    public Shelter(UUID id, String name, String aboutShelter, String address, String workSchedule, String safetyMeasures, String securityData, List<Animal> animals) {
        super(id);
        this.name = name;
        this.aboutShelter = aboutShelter;
        this.address = address;
        this.workSchedule = workSchedule;
        this.safetyMeasures = safetyMeasures;
        this.securityData = securityData;
        this.animals = animals;
    }
}
