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
    @Column(name = "shelter_information")
    private String shelterInformation;
    @Column(name = "shelter_address")
    private String shelterAddress;
    @Column(name = "work_schedule_shelter")
    private String workScheduleShelter;
    @Column(name = "safety_measures_in_shelter")
    private String safetyMeasuresInShelter;
    @Column(name = "security_data")
    private String securityData;
    @Column(name = "contact_for_communication")
    private String contactForCommunication;

    @Builder
    public Shelter(UUID id, String shelterInformation,
                   String shelterAddress, String workScheduleShelter,
                   String safetyMeasuresInShelter, String securityData,
                   String contactForCommunication) {
        super(id);
        this.shelterInformation = shelterInformation;
        this.shelterAddress = shelterAddress;
        this.workScheduleShelter = workScheduleShelter;
        this.safetyMeasuresInShelter = safetyMeasuresInShelter;
        this.securityData = securityData;
        this.contactForCommunication = contactForCommunication;
    }
}
