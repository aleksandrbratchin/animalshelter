package ru.teamfour.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dto.animal.AnimalDto;
import ru.teamfour.dto.shelter.ShelterAddDto;
import ru.teamfour.dto.shelter.ShelterInfoDto;
import ru.teamfour.service.api.animal.AnimalService;
import ru.teamfour.service.impl.animal.AnimalServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    private final AnimalService service;

    public AnimalController(@Qualifier("animalServiveIml") AnimalService service) {
        this.service = service;
    }

    @Operation(
            summary = "Создание животного",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "создание животного",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Животное"
    )

    @PostMapping
    public ResponseEntity<?> add(
            @RequestBody AnimalDto animalDto
    ){
        return ResponseEntity.ok(
                service.create(animal)
        );
    }

    @Operation(
            summary = "Удаление животного по ID",
            responses = {@ApiResponse(
                    responseCode = "200"

            )},
            tags = "Животное"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") UUID id
    ) {
        return ResponseEntity.ok(
                "Животное удалено!"
        );
    }

    @Operation(
            summary = "Изменение данных о животном",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Животное с измененными данными",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Животное"
    )
    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") UUID id,
            @RequestBody AnimalDto animalDto
    ) {
        return ResponseEntity.ok(
                service.put(id, animalDto)
        );
    }

    @Operation(
            summary = "Список животных",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Список животных",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Animal.class)
                            )
                    )
            )},
            tags = "Животное"
    )
    @GetMapping("/All")
    public ResponseEntity<List<AnimalDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
            summary = "Поиск животного по усыновителю",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Найденное животное по усыновителю",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Животное")

    @GetMapping("/nameAdoption")
    public ResponseEntity<AnimalDto> findByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(service.findByAdopted(name));
    }

    @Operation(
            summary = "Список животных по типу",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Список животных по типу",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Animal.class)
                            )
                    )
            )},
            tags = "Животное"
    )
    @GetMapping("/AllType")
    public ResponseEntity<List<AnimalDto>> findAll(@PathVariable("type") String type) {
        return ResponseEntity.ok(service.findAllByType(type));
    }





}