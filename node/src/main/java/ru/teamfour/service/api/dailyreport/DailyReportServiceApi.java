package ru.teamfour.service.api.dailyreport;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.dailyreport.DailyReportStatus;

import java.util.List;
import java.util.UUID;

@Service
public interface DailyReportServiceApi {

    DailyReport save(DailyReport shelterDto);

    List<DailyReport> findByReportStatus(DailyReportStatus reportStatus);

    DailyReport findById(@NotNull UUID id);

    String getClientChat(@NotNull UUID id);
}
