package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.dailyreport.DailyReportStatus;

import java.util.List;
import java.util.UUID;

public interface DailyReportRepository extends JpaRepository<DailyReport, UUID> {

    List<DailyReport> findByReportStatus(DailyReportStatus reportStatus);

}
