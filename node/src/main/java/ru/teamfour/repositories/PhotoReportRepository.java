package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.drivingdirections.DrivingDirections;
import ru.teamfour.dao.entity.photoreport.PhotoReport;

import java.util.Optional;
import java.util.UUID;

public interface PhotoReportRepository extends JpaRepository<PhotoReport, UUID> {
}
