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
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalInfoDto;
import ru.teamfour.service.impl.adoptionanimal.AdoptionProcessAnimalService;
import ru.teamfour.service.impl.user.UserService;

import java.util.List;
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

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable(value = "userId") UUID id) {
        return userService.getUser(id);
    }

    @Operation(
            summary = "СОЗДАТЬ ПРОЦЕСС УСЫНОВЛЕНИЯ",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Информация о усыновлении",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Процесс усыновления"
    )
    @PostMapping
    public ResponseEntity<AdoptionProcessAnimalInfoDto> add(
            @RequestBody AdoptionProcessAnimalCreateDto adoptionProcessAnimalCreateDto
    ) {
        return ResponseEntity.ok(
                adoptionAnimalService.createAdoption(adoptionProcessAnimalCreateDto)
        );
    }

    @Operation(
            summary = "ПРОДЛИТЬ ПРОЦЕСС УСЫНОВЛЕНИЯ НА 14 ДНЕЙ",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Информация о усыновлении",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Процесс усыновления"
    )
    @GetMapping("/addfourteendays/{adoptionAnimalId}")
    public ResponseEntity<AdoptionProcessAnimalInfoDto> addfourteendays(
            @PathVariable(value = "adoptionAnimalId") UUID id
    ) {
        return ResponseEntity.ok(
                adoptionAnimalService.addfourteendays(id)
        );
    }

    @Operation(
            summary = "ПРОДЛИТЬ ПРОЦЕСС УСЫНОВЛЕНИЯ НА 30 ДНЕЙ",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Информация о усыновлении",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Процесс усыновления"
    )

    @GetMapping("/addthirtydays/{adoptionAnimalId}")
    public ResponseEntity<AdoptionProcessAnimalInfoDto> addthirtydays(
            @PathVariable(value = "adoptionAnimalId") UUID id
    ) {
        return ResponseEntity.ok(
                adoptionAnimalService.addthirtydays(id)
        );
    }

    @Operation(
            summary = "ОДОБРИТЬ ПРОЦЕСС УСЫНОВЛЕНИЯ",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Информация о усыновлении",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Процесс усыновления"
    )
    @GetMapping("/approved/{adoptionAnimalId}")
    public ResponseEntity<AdoptionProcessAnimalInfoDto> approved(
            @PathVariable(value = "adoptionAnimalId") UUID id
    ) {
        return ResponseEntity.ok(
                adoptionAnimalService.approved(id)
        );
    }

    @Operation(
            summary = "ОТКЛОНИТЬ ПРОЦЕСС УСЫНОВЛЕНИЯ",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Информация о усыновлении",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Процесс усыновления"
    )
    @GetMapping("/rejected/{adoptionAnimalId}")
    public ResponseEntity<AdoptionProcessAnimalInfoDto> rejected(
            @PathVariable(value = "adoptionAnimalId") UUID id
    ) {
        return ResponseEntity.ok(
                adoptionAnimalService.rejected(id)
        );
    }

    @Operation(
            summary = "УСЫНОВЛЕНИЯ ПО КОТОРЫМ НЕОБХОДИМО ПРИНЯТЬ РЕШЕНИЕ",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Информация о процессе усыновления",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )
            )},
            tags = "Процесс усыновления"
    )
    @GetMapping("/findAllActiveAdoptions")
    public ResponseEntity<List<AdoptionProcessAnimalInfoDto>> findAllActiveAdoptions() {
        return ResponseEntity.ok(
                adoptionAnimalService.receiveAdoptionsOnWhichADecisionNeedsToBeMade()
        );
    }

}
