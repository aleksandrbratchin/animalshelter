package ru.teamfour.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.user.User;

@RestController
public class AdoptionAnimalsController {

    @GetMapping("{adoptionAnimalsId}")
    public ResponseEntity<AdoptionAnimals> getAdoptionAnimals(@PathVariable Long adoptionAnimalsId) {
        return null;
    }

    @PostMapping
    public ResponseEntity<AdoptionAnimals> createAdoptionAnimals(@RequestBody AdoptionAnimals adoptionAnimals) {
        return null;
    }

    @DeleteMapping("{adoptionAnimalsId}")
    public ResponseEntity<AdoptionAnimals> deleteAdoptionAnimals(@PathVariable Long AdoptionAnimalsId) {
        return null;
    }

    @GetMapping("{adoptionAnimalsId}")
    public ResponseEntity<AdoptionAnimals> getAdoptionAnimals(@PathVariable Long adoptionAnimalsId) {
        return null;
    }

}
