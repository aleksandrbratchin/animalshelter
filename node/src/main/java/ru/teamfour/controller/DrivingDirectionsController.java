package ru.teamfour.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @PostMapping("/{shelterId}/avatar")
    public ResponseEntity<?> uploadDirections(@PathVariable UUID shelterId, @RequestParam byte[] data) throws IOException {
        service.createDrivingDirections(shelterId, data);
        return ResponseEntity.ok().build();
    }
}
