package ru.teamfour.controller;

import org.springframework.web.bind.annotation.*;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionAnimal;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.service.AdoptionAnimalService;
import ru.teamfour.service.impl.user.UserService;

import java.util.UUID;

@RestController
@RequestMapping("adoptionanimal")
public class AdoptionAnimalsController {

    private final AdoptionAnimalService adoptionAnimalService;
    private final UserService userService;

    public AdoptionAnimalsController(AdoptionAnimalService adoptionAnimalService, UserService userService) {
        this.adoptionAnimalService = adoptionAnimalService;
        this.userService = userService;
    }

    @GetMapping("/{adoptionAnimalId}")
    public AdoptionAnimal getAdoptionAnimal(@PathVariable(value = "adoptionAnimalId") UUID id) {
        return adoptionAnimalService.findById(id);

    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable(value = "userId") UUID id) {
        return  userService.getUser(id);
    }
}
