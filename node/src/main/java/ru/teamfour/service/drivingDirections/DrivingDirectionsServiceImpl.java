package ru.teamfour.service.drivingDirections;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.teamfour.dao.entity.shelters.DrivingDirections;
import ru.teamfour.dao.entity.shelters.Shelter;
import ru.teamfour.repositories.DrivingDirectionsRepository;
import ru.teamfour.service.shelter.ShelterServiceImpl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
@Service
@Transactional
public class DrivingDirectionsServiceImpl {
    @Value("${path.to.drivingDirections.folder}")
    private String drivingDirectionsDir;
    private final ShelterServiceImpl shelterService;
    private final DrivingDirectionsRepository drivingDirectionsRepository
            ;

    public DrivingDirectionsServiceImpl(ShelterServiceImpl shelterService, DrivingDirectionsRepository drivingDirectionsRepository
    ) {
        this.shelterService = shelterService;
        this.drivingDirectionsRepository
                = drivingDirectionsRepository
        ;
    }

    public void uploadDrivingDirections(Long shelterId, MultipartFile avatarFile) throws IOException {

        Shelter shelter = shelterService.find(shelterId);
        Path filePath = Path.of(drivingDirectionsDir, shelter + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        DrivingDirections drivingDirections = findDrivingDirectionsByShelterId(shelterId);
        drivingDirections.setShelter(shelter);
        drivingDirections.setFilePath(filePath.toString());
        drivingDirections.setFileSize(drivingDirectionsFile.getSize());
        drivingDirections.setMediaType(drivingDirectionsFile.getContentType());
        // drivingDirections.setData(drivingDirectionsFile.getBytes());
        drivingDirections.setData(generateDataForDB(filePath));
        drivingDirectionsRepository
                .save(drivingDirections);
    }

    private String getExtensions(String fileName) {

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public DrivingDirections findDrivingDirectionsByShelterId(Long shelterId) {

        return drivingDirectionsRepository.findByShelterId(shelterId).orElse(new DrivingDirections());
    }
}
