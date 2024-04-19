package ru.teamfour.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.teamfour.service.api.animal.AnimalService;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(@Qualifier("animalServiceImpl") AnimalService animalService) {
        this.animalService = animalService;
    }

   /* @Operation(
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
                service.create(animalDto)
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
                service.update(id, animalDto)
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
    }*/


}
