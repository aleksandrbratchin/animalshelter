package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.user.Volunteer;

import java.util.UUID;

public interface VolunteerRepository extends JpaRepository<Volunteer, UUID> {

}