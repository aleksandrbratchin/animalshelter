package ru.teamfour.mappers.adoptionanimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.dailyreport.DailyReport;
import ru.teamfour.dao.entity.dailyreport.DailyReportStatus;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalInfoDto;
import ru.teamfour.mappers.animal.AnimalMapper;
import ru.teamfour.mappers.user.UserInfoMapper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserInfoMapper.class, AnimalMapper.class})
@Getter
@Setter
@NoArgsConstructor
public abstract class AdoptionProcessAnimalInfoMapper {

    @Mappings({
            @Mapping(source = "date", target = "date", dateFormat = "dd.MM.yyyy"),
            @Mapping(target = "daysOfVerification", expression = "java(daysOfVerification(processAnimal))"),
            @Mapping(target = "daysRejected", expression = "java(daysRejected(processAnimal.getDailyReports()))"),
            @Mapping(target = "daysApproved", expression = "java(daysApproved(processAnimal.getDailyReports()))"),
            @Mapping(target = "daysMissed", expression = "java(calculateDaysMissed(processAnimal))")
    })
    public abstract AdoptionProcessAnimalInfoDto toDto(AdoptionProcessAnimal processAnimal);

    // Метод для вычисления daysMissed
    protected int calculateDaysMissed(AdoptionProcessAnimal processAnimal) {
        int daysOfVerification = daysOfVerification(processAnimal);
        int daysRejected = daysRejected(processAnimal.getDailyReports());
        int daysApproved = daysApproved(processAnimal.getDailyReports());

        return daysOfVerification - daysRejected + daysApproved;
    }

    protected int daysOfVerification(AdoptionProcessAnimal processAnimal) {
        LocalDate firstDate = processAnimal.getDate_create().toLocalDate();
        LocalDate lastDate = processAnimal.getDate();
        return (int) ChronoUnit.DAYS.between(firstDate, lastDate);
    }

    protected int daysRejected(List<DailyReport> dailyReports) {
        return (int) dailyReports.stream()
                .filter(dailyReport -> dailyReport.getReportStatus().equals(DailyReportStatus.NOT_APPROVED))
                .count();
    }

    protected int daysApproved(List<DailyReport> dailyReports) {
        return (int) dailyReports.stream()
                .filter(dailyReport -> dailyReport.getReportStatus().equals(DailyReportStatus.APPROVED))
                .count();
    }

}
