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
    @Column(name="informatuon")
    private String information;

}
