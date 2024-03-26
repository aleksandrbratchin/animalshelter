package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.user.Client;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

}