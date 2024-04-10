package ru.teamfour.dao.entity.infoForAdoption;

import jakarta.persistence.*;
import lombok.*;
import ru.teamfour.dao.entity.ParentUUIDEntity;
import ru.teamfour.dao.entity.animal.TypeAnimal;

import java.util.UUID;

/**
 * класс с полями различных рекомендаций для усыновителей
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "info_for_adoption")
@AllArgsConstructor
public class InfoForAdoption extends ParentUUIDEntity {
    /**
     * тип животных в приюте
     */
    @Column(name = "type_of_animal", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeAnimal typeOfAnimal;
    /**
     * /**
     * Причины отказа в усыновлении
     */
    @Column(name = "reasons_for_refusal_of_adoption") //Причины отказа в усыновлении
    private String reasonsForRefusalOfAdoption;
    /**
     * Транспортировка животного
     */
    @Column(name = "transportation")
    private String transportation;
    /**
     * Обустройство дома для взрослого животного
     */
    @Column(name = " homeImprovementForAdultAnimal")
    private String homeImprovementForAdultAnimal;
    /**
     * Обустройство дома для молодого животного
     */
    @Column(name = "homeImprovementForYoungAnimal")
    private String homeImprovementForYoungAnimal;
    /**
     * советы специалиста
     */
    @Column(name = "tipsFromSpecialist")
    private String tipsFromSpecialist;
    /**
     * Обустройство дома для животного с ограниченными возможностями
     */
    @Column(name = " homeImprovementForAnimalWithDisabilities")
    private String homeImprovementForAnimalWithDisabilities;
    /**
     * список специалистов
     */
    @Column(name = "list_specialists")
    private String listSpecialists;
    /**
     * Правила знакомства с животным
     */
    @Column(name = "rules_animals")
    private String rulesAnimals;
    /**
     * Список документов для усыновления
     */
    @Column(name = " list_documents") //   Список документов для усыновления
    private String listDocuments;
    //@Builder

    public InfoForAdoption(UUID id, TypeAnimal typeOfAnimal,
                           String reasonsForRefusalOfAdoption,
                           String transportation,
                           String homeImprovementForAdultAnimal,
                           String homeImprovementForYoungAnimal,
                           String tipsFromSpecialist,
                           String homeImprovementForAnimalWithDisabilities,
                           String listSpecialists,
                           String rulesAnimals,
                           String listDocuments) {
        super(id);
        this.typeOfAnimal = typeOfAnimal;
        this.reasonsForRefusalOfAdoption = reasonsForRefusalOfAdoption;
        this.transportation = transportation;
        this.homeImprovementForAdultAnimal = homeImprovementForAdultAnimal;
        this.homeImprovementForYoungAnimal = homeImprovementForYoungAnimal;
        this.tipsFromSpecialist = tipsFromSpecialist;
        this.homeImprovementForAnimalWithDisabilities = homeImprovementForAnimalWithDisabilities;
        this.listSpecialists = listSpecialists;
        this.rulesAnimals = rulesAnimals;
        this.listDocuments = listDocuments;
    }
}