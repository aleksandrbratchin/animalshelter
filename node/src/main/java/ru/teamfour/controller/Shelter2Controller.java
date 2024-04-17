package ru.teamfour.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dto.shelter.ShelterAddDto;
import ru.teamfour.dto.shelter.ShelterInfoDto;
import ru.teamfour.service.impl.shelter.ShelterServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shelter2")
public class Shelter2Controller {
    private final ShelterServiceImpl service;

    public Shelter2Controller(ShelterServiceImpl service) {
        this.service = service;
    }
    @Operation(
            summary = "СОЗДАНИЕ ПРИЮТА",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "созданный приют",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Приют"
    )
    @PostMapping()
    public ResponseEntity<?> add(
            @RequestBody ShelterAddDto shelterDto
    ) {
        try {
            return ResponseEntity.ok(
                    service.create(shelterDto)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Operation(
            summary = "УДАЛЕНИЕ  ПРИЮТА ПО ID",
            responses = {@ApiResponse(
                    responseCode = "200"

            )},
            tags = "Приют"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") UUID id
    ) {
        try {
            service.remove(id);
            return ResponseEntity.ok(
                    "Приют удален!"
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "ИЗМЕНЕНИЕ ДАННЫХ О ПРИЮТЕ",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Приют с измененными данными",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Приют"
    )
    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") UUID id,
            @RequestBody ShelterAddDto student
    ) {
        try {
            return ResponseEntity.ok(
                    service.update(id, student)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "СПИСОК ПРИЮТОВ",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "список приютов",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Shelter.class)
                            )
                    )
            )},
            tags = "Приют"
    )
    @GetMapping("/All")
    public ResponseEntity<List<ShelterInfoDto>> findAll() {
        return ResponseEntity.ok(service.findAllDto());
    }

    @Operation(
            summary = "ПОИСК ПРИЮТА ПО НАЗВАНИЮ",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Найденный приют",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Приют")
    @GetMapping("/name/{name}")
    public ResponseEntity<ShelterInfoDto> findByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(service.findByNameDto(name));
    }


}
