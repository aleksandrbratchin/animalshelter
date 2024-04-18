package ru.teamfour.service.api.dailyreport;

import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.dailyreport.DailyReport;

@Service
public interface DailyReportService {
    DailyReport save(DailyReport shelterDto);
}
