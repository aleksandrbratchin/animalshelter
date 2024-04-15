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

import java.time.LocalDate;
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
@Table(name = "adoption_process_animal")
public class AdoptionProcessAnimal extends AuditEntity {

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
     * Дата окончания проверки
     */
    @Column(name = "date", nullable = false)
    private LocalDate date;

    /**
     * adoptionStatus - статус усыновления {@link AdoptionProcessStatus}
     */

    @Enumerated(EnumType.STRING)
    private AdoptionProcessStatus adoptionProcessStatus;

    /***
     * Ежедневные отчеты
     */
    @OneToMany(
            mappedBy = "adoptionProcessAnimal",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DailyReport> adoptions = new ArrayList<>();

    @Builder
    public AdoptionProcessAnimal(UUID id, User user, Animal animal, AdoptionProcessStatus adoptionProcessStatus, List<DailyReport> adoptions) {
        super(id);
        this.user = user;
        this.animal = animal;
        this.adoptionProcessStatus = adoptionProcessStatus;
        this.adoptions = adoptions;
    }
}
