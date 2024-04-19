package ru.teamfour.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalCreateDto;
import ru.teamfour.dto.shelter.ShelterAddDto;
import ru.teamfour.service.impl.adoptionanimal.AdoptionProcessAnimalService;
import ru.teamfour.service.impl.user.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/adoptionanimal")
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


    @PostMapping()
    public ResponseEntity<?> add(
            @RequestBody AdoptionProcessAnimalCreateDto adoptionProcessAnimalCreateDto
    ) {
        adoptionAnimalService.createAdoption(adoptionProcessAnimalCreateDto);
        return ResponseEntity.ok(
                "Начат процесс усыновления!"
        );
    }

}
