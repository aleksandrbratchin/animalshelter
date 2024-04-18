package ru.teamfour.dao.entity.dailyreport;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamfour.dao.entity.AuditEntity;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.photoreport.PhotoReport;
import ru.teamfour.dao.entity.user.User;

/**
 * Ежедневные отчеты о том как происходит процесс усыновления
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "daily_report")
public class DailyReport extends AuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adoption_animal_id")
    private AdoptionProcessAnimal adoptionProcessAnimal;

    /**
     * Волонтер который изменил статус отчета
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id")
    private User volunteer;

    /**
     * reportStatus - статус ежедневного отчета {@link DailyReportStatus}
     */
    @Column(name = "report_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DailyReportStatus reportStatus;

    /**
     * Текст отчета
     */
    @Column(name = "report_text", columnDefinition = "TEXT")
    private String reportText;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_report_id", referencedColumnName = "id")
    private PhotoReport photoReport;

}
