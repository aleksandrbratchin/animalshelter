package ru.teamfour.dao.entity.shelter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamfour.dao.entity.ParentEntity;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shelters")
public class Shelter extends ParentEntity {
    @Column(name = "story_of_shelter")
    private String storyOfShelter;
    @Column(name = "shelter_address")
    private String shelterAddress;
    @Column(name = "work_schedule_shelter")
    private String workScheduleShelter;
    @Column(name = "safety_measures_in_shelter")
    private String safetyMeasuresInShelter;
    @Column(name = "security_data")
    private String securityData;


    @Builder
    public Shelter(UUID id, String storyOfShelter,
                   String shelterAddress, String workScheduleShelter,
                   String safetyMeasuresInShelter, String securityData
    ) {
        super(id);
        this.storyOfShelter = storyOfShelter;
        this.shelterAddress = shelterAddress;
        this.workScheduleShelter = workScheduleShelter;
        this.safetyMeasuresInShelter = safetyMeasuresInShelter;
        this.securityData = securityData;
    }
}
