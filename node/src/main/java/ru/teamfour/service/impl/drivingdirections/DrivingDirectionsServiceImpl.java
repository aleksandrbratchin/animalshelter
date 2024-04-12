package ru.teamfour.service.impl.drivingdirections;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.drivingdirections.DrivingDirections;
import ru.teamfour.repositories.DrivingDirectionsRepository;
import ru.teamfour.service.api.drivingdirections.DrivingDirectionsService;

import java.util.UUID;

@Service
@Transactional
public class DrivingDirectionsServiceImpl implements DrivingDirectionsService {

    private final DrivingDirectionsRepository drivingDirectionsRepository;

    public DrivingDirectionsServiceImpl(DrivingDirectionsRepository drivingDirectionsRepository) {
        this.drivingDirectionsRepository = drivingDirectionsRepository;
    }

    @Override
    public DrivingDirections findByShelterId(UUID shelterId) {
        return drivingDirectionsRepository.findByShelterId(shelterId).orElse(new DrivingDirections());
    }
}
