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
    @Column
    private String shelterInformation;
    @Column
    private String shelterAddress;
    @Column
    private String workScheduleShelter;
    @Column
    private String safetyMeasuresInShelter;
    @Column
    private String securityData;
    @Column
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
