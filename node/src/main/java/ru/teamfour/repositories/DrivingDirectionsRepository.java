package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.drivingdirections.DrivingDirections;

import java.util.Optional;
import java.util.UUID;

public interface DrivingDirectionsRepository extends JpaRepository<DrivingDirections, Long> {
    Optional<DrivingDirections> findByShelterId(UUID shelterId);

}
