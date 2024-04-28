package ru.teamfour.service.impl.drivingdirections;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.teamfour.dao.entity.drivingdirections.DrivingDirections;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.repositories.DrivingDirectionsRepository;
import ru.teamfour.repositories.ShelterRepository;
import ru.teamfour.service.api.drivingdirections.DrivingDirectionsService;

import java.io.*;
import java.util.UUID;

@Service
@Transactional
public class DrivingDirectionsServiceImpl implements DrivingDirectionsService {

    private final DrivingDirectionsRepository drivingDirectionsRepository;
    private final ShelterRepository shelterRepository;

    public DrivingDirectionsServiceImpl(DrivingDirectionsRepository drivingDirectionsRepository, ShelterRepository shelterRepository) {
        this.drivingDirectionsRepository = drivingDirectionsRepository;
        this.shelterRepository = shelterRepository;
    }

    /**
     * нахождение объекта {@link DrivingDirections} по id Shelter
     *
     * @param shelterId идентификатор приюта
     * @return возвращает найденный объект
     */
    @Override
    public DrivingDirections findByShelterId(UUID shelterId) {
        return drivingDirectionsRepository.findByShelterId(shelterId)
                .orElse(new DrivingDirections());
    }

    /**
     * Создание схемы проезда и запись в БД
     *
     * @param shelterId Id приюта
     * @param data      схема
     */
    @Override
    public void createDrivingDirections(UUID shelterId, MultipartFile data) throws IOException {

        Shelter shelter = shelterRepository.findById(shelterId).orElseThrow(IllegalArgumentException::new);
        DrivingDirections directions = findByShelterId(shelterId);
        directions.setShelter(shelter);
        directions.setData(data.getBytes());
        drivingDirectionsRepository.save(directions);

    }

    /**
     * удаление схемы проезда
     *
     * @param shelterId идентификатор приюта
     */
    @Override
    public void deleteDrivingDirections(UUID shelterId) {

        drivingDirectionsRepository.deleteByShelterId(shelterId);
    }

    /**
     * Замена схемы проезда
     *
     * @param idShelter  идентификатор приюта
     * @param data фото
     */
    @Override
    public void put(UUID idShelter, MultipartFile data) throws IOException {
        DrivingDirections directions = drivingDirectionsRepository.findByShelterId(idShelter).get();
        directions.setData(data.getBytes());
        drivingDirectionsRepository.save(directions);

    }

}
