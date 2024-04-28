package ru.teamfour.service.api.drivingdirections;

import ru.teamfour.dao.entity.drivingdirections.DrivingDirections;

import java.util.UUID;

public interface DrivingDirectionsService {

    DrivingDirections findByShelterId(UUID shelterId);

}
