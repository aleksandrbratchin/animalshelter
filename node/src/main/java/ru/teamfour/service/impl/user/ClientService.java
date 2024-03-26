package ru.teamfour.service.impl.user;

import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.user.Client;
import ru.teamfour.repositories.ClientRepository;

@Service
public class ClientService {
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Client add(Client client){
        return repository.save(client);
    }
}
