package ru.teamfour.service.impl.drivingDirections;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.drivingDirections.DrivingDirections;
import ru.teamfour.repositories.DrivingDirectionsRepository;
import ru.teamfour.service.api.drivingDirections.DrivingDirectionsService;

import java.util.UUID;

@Service
@Transactional
public class DrivingDirectionsServiceImpl implements DrivingDirectionsService {
    //надо подумать над Value
/*    @Value("/drivingDirections")
    private String drivingDirectionsDir;
    private final ShelterServiceImpl shelterService;*/
    private final DrivingDirectionsRepository drivingDirectionsRepository;

    public DrivingDirectionsServiceImpl(DrivingDirectionsRepository drivingDirectionsRepository) {
        this.drivingDirectionsRepository = drivingDirectionsRepository;
    }

/*    public DrivingDirectionsServiceImpl(ShelterServiceImpl shelterService,
                                        DrivingDirectionsRepository drivingDirectionsRepository) {
        this.shelterService = shelterService;
        this.drivingDirectionsRepository = drivingDirectionsRepository
        ;
    }

    @Override
    public void uploadDrivingDirections(UUID shelterId, MultipartFile drivingDirectionsFile) throws IOException {

        Shelter shelter = shelterService.find(shelterId);
        Path filePath = Path.of(drivingDirectionsDir, shelter + "." + getExtensions(drivingDirectionsFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = drivingDirectionsFile.getInputStream();
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

    private byte[] generateDataForDB(Path filePath) throws IOException {
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();
            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();

        }
    }*/

    @Override
    public DrivingDirections findByShelterId(UUID shelterId) {
        return drivingDirectionsRepository.findByShelterId(shelterId).orElse(new DrivingDirections());
    }
}
