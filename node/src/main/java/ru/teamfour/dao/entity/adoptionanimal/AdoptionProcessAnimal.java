package ru.teamfour.dao.entity.adoptionanimal;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamfour.dao.entity.AuditEntity;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dao.entity.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    //todo можно потом переделать и брать из application.yml
    public static final int AUDIT_DAYS = 30;

    /**
     * Клиент который усыновляет животное
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Приют
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

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
    private LocalDate date = LocalDate.now().plusDays(AdoptionProcessAnimal.AUDIT_DAYS);

    /**
     * adoptionStatus - статус усыновления {@link AdoptionProcessStatus}
     */
    @Enumerated(EnumType.STRING)
    private AdoptionProcessStatus adoptionProcessStatus = AdoptionProcessStatus.PROCESS_ADOPTION;

    /***
     * Ежедневные отчеты
     */
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "adoptionProcessAnimal",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DailyReport> dailyReports = new ArrayList<>();

    @Builder
    public AdoptionProcessAnimal(UUID id, User user, Shelter shelter, Animal animal, LocalDate date, AdoptionProcessStatus adoptionProcessStatus, List<DailyReport> dailyReports) {
        super(id);
        this.user = user;
        this.shelter = shelter;
        this.animal = animal;
        this.date = Optional.ofNullable(date).orElse(LocalDate.now().plusDays(AdoptionProcessAnimal.AUDIT_DAYS));
        this.adoptionProcessStatus = adoptionProcessStatus == null ? AdoptionProcessStatus.PROCESS_ADOPTION : adoptionProcessStatus;
        this.dailyReports = new ArrayList<>();
    }

    public DailyReport getLastDailyReport(LocalDate now) {
        List<DailyReport> list = dailyReports.stream()
                .filter(dailyReport ->
                        dailyReport.getDate_report().equals(now)
                ).toList();
        if (list.size() == 1) {
            return list.get(0);
        }
        if (list.size() > 1) {
            throw new RuntimeException(); //todo мое исключение
        }
        return null;
    }
}
