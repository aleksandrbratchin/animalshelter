package ru.teamfour.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.service.impl.shelter.ShelterServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/shelter")
public class ShelterController {
    private final ShelterServiceImpl service;

    public ShelterController(ShelterServiceImpl service) {
        this.service = service;
    }

    @Operation(
            summary = "ПОИСК ПРИЮТА ПО ИМЕНИ ПРИЮТА",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "найденный приют ",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Приюты")
    @GetMapping("/getName")
    public ResponseEntity<Shelter> getShelterByName(@RequestParam(name = "Название приюта") String name) {
        return ResponseEntity.ok(service.findByName(name));
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
            tags = "Приюты"
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<Shelter>> getAllShelters() {
        return ResponseEntity.ok(service.findAll());
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
            tags = "Приюты"
    )
    @PostMapping("/add")
    public ResponseEntity<Shelter> createStudent(@RequestParam(name = "Название приюта") String name,
                                                 @RequestParam(name = "Тип животного") TypeAnimal typeAnimal,
                                                 @RequestParam(name = "Рассказ о приюте") String aboutShelter,
                                                 @RequestParam(name = "Адрес") String address,
                                                 @RequestParam(name = "Правила поведения на территории приюта") String safetyMeasures,
                                                 @RequestParam(name = "Данные охраны для оформления пропуска") String securityData,
                                                 @RequestParam(name = "Расписание работы") String workSchedule
    ) {
        Shelter createdShelter = service.add(name, typeAnimal, aboutShelter,
                address, safetyMeasures, securityData, workSchedule);
        return ResponseEntity.ok(createdShelter);
    }

    @Operation(
            summary = "УДАЛЕНИЕ  ПРИЮТА ПО ИМЕНИ ПРИЮТА",
            responses = {@ApiResponse(
                    responseCode = "200"

            )},
            tags = "Приюты"
    )

    @DeleteMapping("/deleteByName")
    public ResponseEntity deleteByName(@RequestParam(name = "Название приюта") String name) {
        service.removeByName(name);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "ИЗМЕНЕНИЕ ДАННЫХ О ПРИЮТЕ С ОБЯЗАТЕЛЬНЫМ ЗАПОЛНЕНИЕМ ПОЛЯ ИМЕНИ ПРИЮТА",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "приют с измененными данными",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Приюты"
    )

    @PutMapping("/change")
    public ResponseEntity<Shelter> changeAbout(@RequestParam(name = "Название приюта") String name,
                                               @RequestParam(required = false, name = "Рассказ о приюте") String about,
                                               @RequestParam(required = false, name = "Данные охраны для оформления пропуска") String security,
                                               @RequestParam(required = false, name = "Тип животного") TypeAnimal typeAnimal,
                                               @RequestParam(required = false, name = "Адрес") String address,
                                               @RequestParam(required = false, name = "Правила поведения на территории приюта") String safetyMeasures,
                                               @RequestParam(required = false, name = "Расписание работы") String workSchedule) {
        if (name != null && !name.isBlank()) {
            if (about != null && !about.isBlank()) {
                Shelter shelter = service.changeAboutShelter(name, about);

            }
            if (security != null && !security.isBlank()) {
                Shelter shelter = service.changeSecurity(name, security);
            }
            if (typeAnimal != null) {
                Shelter shelter = service.changeTypeAnimal(name, typeAnimal);
            }
            if (address != null && !address.isBlank()) {
                Shelter shelter = service.changeAddress(name, address);
            }
            if (safetyMeasures != null && !safetyMeasures.isBlank()) {
                Shelter shelter = service.changeSafetyMeasures(name, safetyMeasures);
            }
            if (workSchedule != null && !workSchedule.isBlank()) {
                Shelter shelter = service.changeWorkSchedule(name, workSchedule);
            }
        } else ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).build();
        return ResponseEntity.ok(service.findByName(name));
    }

}

