package ru.teamfour.dao.entity.dailyreport;

import jakarta.persistence.*;
import lombok.*;
import ru.teamfour.dao.entity.AuditEntity;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.photoreport.PhotoReport;
import ru.teamfour.dao.entity.user.User;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * Ежедневные отчеты о том как происходит процесс усыновления
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "daily_report")
public class DailyReport extends AuditEntity implements Comparable<DailyReport> {

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

    @OneToOne(fetch = FetchType.LAZY
            , cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_report_id", referencedColumnName = "id")
    private PhotoReport photoReport;

    /**
     * дата отчета
     */
    @Column(name = "date_report", nullable = false)
    private LocalDate date_report = LocalDate.now();

    @Builder
    public DailyReport(UUID id, AdoptionProcessAnimal adoptionProcessAnimal, User volunteer, DailyReportStatus reportStatus, String reportText, PhotoReport photoReport) {
        super(id);
        this.adoptionProcessAnimal = adoptionProcessAnimal;
        this.volunteer = volunteer;
        this.reportStatus = Optional.ofNullable(reportStatus).orElse(DailyReportStatus.NOT_APPROVED);
        this.reportText = reportText;
        this.photoReport = photoReport;
    }

    @Override
    public int compareTo(DailyReport o) {
        return this.getDate_report().compareTo(o.date_report);
    }
}
