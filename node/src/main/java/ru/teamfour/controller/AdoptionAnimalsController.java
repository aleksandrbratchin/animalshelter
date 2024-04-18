package ru.teamfour.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.AdoptionProcessAnimalService;
import ru.teamfour.service.impl.user.UserService;

import java.util.UUID;

@RestController
@RequestMapping("adoptionanimal")
public class AdoptionAnimalsController {

    private final AdoptionProcessAnimalService adoptionAnimalService;
    private final UserService userService;

    public AdoptionAnimalsController(AdoptionProcessAnimalService adoptionAnimalService, UserService userService) {
        this.adoptionAnimalService = adoptionAnimalService;
        this.userService = userService;
    }

    @GetMapping("/{adoptionAnimalId}")
    public AdoptionProcessAnimal getAdoptionAnimal(@PathVariable(value = "adoptionAnimalId") UUID id) {
        return adoptionAnimalService.findById(id);

    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable(value = "userId") UUID id) {
        return userService.getUser(id);
    }
}
