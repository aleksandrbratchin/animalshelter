package ru.teamfour.dao.entity.shelters;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamfour.dao.entity.ParentEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shelters")
public class Shelter extends ParentEntity {
private String shelterInformation;
private String shelterAddress;
private String workScheduleShelter;
private String safetyMeasuresInShelter;
private String securityData;
private String contactForCommunication;


}
