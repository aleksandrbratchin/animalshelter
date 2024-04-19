package ru.teamfour.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
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


    @Operation(
            summary = "СОЗДАТЬ ПРОЦЕСС УСЫНОВЛЕНИЯ",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "", //todo
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Процесс усыновления"
    )
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
