package ru.teamfour.service.impl.drivingdirections;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.drivingdirections.DrivingDirections;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.repositories.DrivingDirectionsRepository;
import ru.teamfour.repositories.ShelterRepository;
import ru.teamfour.service.api.drivingdirections.DrivingDirectionsService;

import java.util.UUID;

@Service
@Transactional
public class DrivingDirectionsServiceImpl implements DrivingDirectionsService {

    private final DrivingDirectionsRepository drivingDirectionsRepository;
    private final ShelterRepository shelterRepository;

    public DrivingDirectionsServiceImpl(DrivingDirectionsRepository drivingDirectionsRepository, ShelterRepository shelterRepository) {
        this.drivingDirectionsRepository = drivingDirectionsRepository;
        this.shelterRepository = shelterRepository;
    }

    @Override
    public DrivingDirections findByShelterId(UUID shelterId) {
        return drivingDirectionsRepository.findByShelterId(shelterId)
                .orElse(new DrivingDirections());
    }

    public DrivingDirections createDrivingDirections(UUID shelterId, byte[] data) {
        Shelter shelter = shelterRepository.findById(shelterId).get();
        DrivingDirections directions = findByShelterId(shelterId);
        directions.setShelter(shelter);
        directions.setData(data);
        return directions;
    }
}
