package ru.teamfour.service.api.dailyreport;

import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.dailyreport.DailyReportStatus;

import java.util.List;

@Service
public interface DailyReportServiceApi {
    DailyReport save(DailyReport shelterDto);

    List<DailyReport> findByReportStatus(DailyReportStatus reportStatus);
}
