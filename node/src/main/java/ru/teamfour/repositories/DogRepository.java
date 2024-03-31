package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.animal.Dog;

import java.util.UUID;

public interface DogRepository extends JpaRepository<Dog, UUID> {
}
