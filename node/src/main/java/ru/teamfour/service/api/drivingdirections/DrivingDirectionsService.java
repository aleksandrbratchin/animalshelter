package ru.teamfour.service.api.drivingdirections;

import org.springframework.web.multipart.MultipartFile;
import ru.teamfour.dao.entity.drivingdirections.DrivingDirections;

import java.io.IOException;
import java.util.UUID;

public interface DrivingDirectionsService {

    DrivingDirections findByShelterId(UUID shelterId);

    void put(UUID idShelter, MultipartFile data) throws IOException;

    void deleteDrivingDirections(UUID shelterId);

    void createDrivingDirections(UUID shelterId, MultipartFile data) throws IOException;
}
