package ru.teamfour.dao.entity.adoptionanimal;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamfour.dao.entity.AuditEntity;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Усыновление животного
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "adoption_animal")
public class AdoptionAnimal extends AuditEntity {

    /**
     * Клиент который усыновляет животное
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * Животное которое усыновляют
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    /**
     * adoptionStatus - статус усыновления {@link AdoptionStatus}
     */
    @Column(name = "adoption_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdoptionStatus adoptionStatus;

    /***
     * Ежедневные отчеты
     */
    @OneToMany(
            mappedBy = "adoptionAnimal",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DailyReport> adoptions = new ArrayList<>();

    @Builder
    public AdoptionAnimal(UUID id, User user, Animal animal, AdoptionStatus adoptionStatus, List<DailyReport> adoptions) {
        super(id);
        this.user = user;
        this.animal = animal;
        this.adoptionStatus = adoptionStatus;
        this.adoptions = adoptions;
    }
}
