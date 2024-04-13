package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.dailyreport.DailyReport;

import java.util.UUID;

public interface DailyReportRepository extends JpaRepository<DailyReport, UUID> {

}
