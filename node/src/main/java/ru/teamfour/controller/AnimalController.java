package ru.teamfour.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dto.animal.AnimalDto;
import ru.teamfour.dto.animal.AnimalUpdateDto;
import ru.teamfour.dto.shelter.ShelterInfoDto;
import ru.teamfour.service.api.animal.AnimalService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(@Qualifier("animalServiceImpl") AnimalService animalService) {
        this.animalService = animalService;
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
    public ResponseEntity<AnimalDto> add(
            @RequestBody AnimalUpdateDto animalUpdateDto
            ){
        return ResponseEntity.ok(
                animalService.create(animalUpdateDto)
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
    public ResponseEntity<AnimalDto> delete(
            @PathVariable("id") UUID id
    ) {
        return ResponseEntity.ok(animalService.delete(id));
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
            @RequestBody AnimalUpdateDto animalUpdateDto
    ) {
        return ResponseEntity.ok(
                animalService.update(id, animalUpdateDto)
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
        return ResponseEntity.ok(animalService.findAllAnimal());
    }

    @Operation(
            summary = "Поиск животных по статусу усыновлены они или нет",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Найденное животное по усыновителю",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Животное")

    @GetMapping("/nameAdoption")
    public ResponseEntity<List<AnimalDto>> findByAdopted() {
        return ResponseEntity.ok(animalService.findByAdopted());
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
    public ResponseEntity<List<AnimalDto>> findAllByType(
            @PathVariable("type") TypeAnimal typeAnimal) {
        return ResponseEntity.ok(animalService.findAllByType(typeAnimal));
    }

}
