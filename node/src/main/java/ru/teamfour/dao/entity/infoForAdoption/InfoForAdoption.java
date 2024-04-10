package ru.teamfour.dao.entity.infoForAdoption;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "info_for_adoption")
@AllArgsConstructor
public class InfoForAdoption {
    @Column(name = "id")
    @Id
    private Integer id;
    @Column(name="reasons_for_refusal_of_adoption") //Причины отказа в усыновлении
    private String reasonsForRefusalOfAdoption;
    @Column(name="transportation") // Транспортировка
    private String transportation;
    @Column(name=" homeImprovementForAdultAnimal") // Обустройство дома для взрослого животного
    private String  homeImprovementForAdultAnimal;
    @Column(name="homeImprovementForPuppy") // Обустройство дома для щенка
    private String homeImprovementForPuppy;
    @Column(name="tipsFromDogHandler") //  Советы кинолога
    private String tipsFromDogHandler;
    @Column(name=" homeImprovementForAnimalWithDisabilities") //  Обустройство дома для животного с ограниченными возможностями
    private String  homeImprovementForAnimalWithDisabilities;
    @Column(name="listDogHandlers") //   список кинологов
    private String listDogHandlers;
    @Column(name="rulesAnimals") //  Правила знакомства с животным
    private String rulesAnimals;
    @Column(name=" listDocuments") //   Список документов для усыновления
    private String listDocuments;

}
