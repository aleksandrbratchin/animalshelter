package ru.teamfour.service.api.drivingdirections;

import ru.teamfour.dao.entity.drivingdirections.DrivingDirections;

import java.util.UUID;

public interface DrivingDirectionsService {
    /*void uploadDrivingDirections(UUID shelterId, MultipartFile drivingDirectionsFile) throws IOException;*/

    DrivingDirections findByShelterId(UUID shelterId);
}
