package ru.teamfour.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.teamfour.dao.entity.drivingdirections.DrivingDirections;
import ru.teamfour.service.impl.drivingdirections.DrivingDirectionsServiceImpl;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/drivingDirections")
public class DrivingDirectionsController {
    private final DrivingDirectionsServiceImpl service;

    public DrivingDirectionsController(DrivingDirectionsServiceImpl service) {
        this.service = service;
    }

    @Operation(
            summary = "СХЕМА ПОЕЗДА",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "загруженная схема проезда"

            )},
            tags = "Схема проезда"
    )
    @PostMapping(value = "/addDirections/{shelterId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadDirections(@PathVariable UUID shelterId, @RequestParam MultipartFile data) throws IOException {
        service.createDrivingDirections(shelterId, data);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "ПОИСК СХЕМЫ ПОЕЗДА ПО ID ПРИЮТА",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "найденная схема проезда"

            )},
            tags = "Схема проезда"
    )
    @GetMapping("/{idShelter}/getDirections")
    public ResponseEntity<?> downloadDrivingDirections(@PathVariable("idShelter") UUID id) {
        DrivingDirections directions = service.findByShelterId(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(directions.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(directions.getData());

    }

    @Operation(
            summary = "УДАЛЕНИЕ  СХЕМЫ ПРОЕЗДА ПО ID ПРИЮТА",
            responses = {@ApiResponse(
                    responseCode = "200"
            )},
            tags = "Схема проезда"
    )
    @DeleteMapping("/{idShelter}/deleteDirections")
    public ResponseEntity<?> delete(
            @PathVariable("idShelter") UUID id
    ) {
        service.deleteDrivingDirections(id);
        return ResponseEntity.ok(
                "Cхема проезда удалена!"
        );
    }

    @Operation(
            summary = "ЗАМЕНА СХЕМЫ ПОЕЗДА",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "загруженная схема проезда"

            )},
            tags = "Схема проезда"
    )
    @PutMapping(value = "/{idShelter}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> putDrivingDirections(@PathVariable("idShelter") UUID id, @RequestParam MultipartFile data) throws IOException {
        service.put(id, data);
        return ResponseEntity.ok().build();
    }

}
