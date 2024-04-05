package ru.teamfour.service.api.drivingDirections;

import org.springframework.web.multipart.MultipartFile;
import ru.teamfour.dao.entity.drivingDirections.DrivingDirections;

import java.io.IOException;
import java.util.UUID;

public interface DrivingDirectionsService {
    void uploadDrivingDirections(UUID shelterId, MultipartFile drivingDirectionsFile) throws IOException;

    DrivingDirections findDrivingDirectionsByShelterId(UUID shelterId);
}
