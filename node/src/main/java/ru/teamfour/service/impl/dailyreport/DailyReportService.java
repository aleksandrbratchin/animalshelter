package ru.teamfour.service.impl.dailyreport;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.dailyreport.DailyReportStatus;
import ru.teamfour.repositories.DailyReportRepository;
import ru.teamfour.service.api.dailyreport.DailyReportServiceApi;

import java.util.List;
import java.util.UUID;

@Service
@Validated
@Transactional
public class DailyReportService implements DailyReportServiceApi {

    private final DailyReportRepository dailyReportRepository;

    public DailyReportService(DailyReportRepository dailyReportRepository) {
        this.dailyReportRepository = dailyReportRepository;
    }

    @Override
    public DailyReport save(DailyReport shelterDto) {
        return dailyReportRepository.save(shelterDto);
    }

    @Override
    public List<DailyReport> findByReportStatus(DailyReportStatus reportStatus) {
        return dailyReportRepository.findByReportStatus(reportStatus);
    }

    @Override
    public DailyReport findById(@NotNull UUID id) {
        return dailyReportRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String getClientChat(@NotNull UUID id) {
        return findById(id).getAdoptionProcessAnimal().getUser().getChatId().toString();
    }

}
