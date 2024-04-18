package ru.teamfour.service.impl.dailyreport;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.repositories.DailyReportRepository;
import ru.teamfour.service.api.dailyreport.DailyReportService;

@Service
@Validated
@Transactional
public class DailyReportServiceApi implements DailyReportService {

    private final DailyReportRepository dailyReportRepository;

    public DailyReportServiceApi(DailyReportRepository dailyReportRepository) {
        this.dailyReportRepository = dailyReportRepository;
    }

    @Override
    public DailyReport save(DailyReport shelterDto) {
        return dailyReportRepository.save(shelterDto);
    }

}
