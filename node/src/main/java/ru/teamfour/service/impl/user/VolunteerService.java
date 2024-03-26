package ru.teamfour.service.impl.user;

import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.user.Volunteer;
import ru.teamfour.repositories.VolunteerRepository;

@Service
public class VolunteerService {
    private final VolunteerRepository repository;

    public VolunteerService(VolunteerRepository repository) {
        this.repository = repository;
    }

    public Volunteer add(Volunteer client) {
        return repository.save(client);
    }
}
