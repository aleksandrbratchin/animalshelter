package ru.teamfour.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionAnimal;

@RestController
public class AdoptionAnimalsController {

    @GetMapping("{adoptionAnimalId}")
    public ResponseEntity<AdoptionAnimal> getAdoptionAnimal(@PathVariable Long adoptionAnimalId) {
        return null;
    }

    @PostMapping
    public ResponseEntity<AdoptionAnimal> createAdoptionAnimals(@RequestBody AdoptionAnimal adoptionAnimal) {
        return null;
    }

    @DeleteMapping("{adoptionAnimalId}")
    public ResponseEntity<AdoptionAnimal> deleteAdoptionAnimal(@PathVariable Long AdoptionAnimalId) {
        return null;
    }

}
