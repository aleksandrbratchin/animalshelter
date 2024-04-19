package ru.teamfour.service.impl.shelter;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dto.shelter.ShelterAddDto;
import ru.teamfour.dto.shelter.ShelterInfoDto;
import ru.teamfour.mappers.shelter.ShelterAddDtoMapper;
import ru.teamfour.mappers.shelter.ShelterDtoMapper;
import ru.teamfour.repositories.ShelterRepository;
import ru.teamfour.service.api.shelter.ShelterService;

import java.util.List;
import java.util.UUID;

@Service
@Validated
@Transactional
@AllArgsConstructor
public class ShelterServiceImpl implements ShelterService {

    protected final ShelterRepository shelterRepository;

    protected final ShelterAddDtoMapper shelterAddDtoMapper;

    protected final ShelterDtoMapper shelterDtoMapper;

    /**
     * метод находит объект {@link Shelter} в БД по UUID
     *
     * @param id {@link UUID}
     * @return возвращает найденный объект
     */
    @Override
    public Shelter findById(@NotNull UUID id) {
        return shelterRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Нет приюта с id = " + id));
    }

    /**
     * метод находит объект {@link Shelter} в БД по названию приюта
     *
     * @param name название приюта
     * @return {@link Shelter} с названием {@code name}
     */
    @Override
    public Shelter findByName(String name) {
        return shelterRepository.findByName(name).orElseThrow(RuntimeException::new); //todo свое исключение кидать
    }

    /**
     * Mетод возвращает список всех приютов
     *
     * @return список всех приютов
     */
    @Override
    public List<Shelter> findAll() {
        return shelterRepository.findAll();
    }

    /**
     * метод возвращает список неусыновленных животных приюта
     *
     * @param id id приюта
     * @return список в формате строки
     */
    @Override
    public List<Animal> findAllAnimalsNotAdoption(@NotNull UUID id) { //todo переписать на SQL
        Shelter shelter = findById(id);
        return shelter.getAnimals().stream().filter(animal -> animal.getAdopted().equals(AdoptionAnimalState.NOT_ADOPTED)).toList();
    }

    /**
     * метод возвращает список приютов по типу животного {@link TypeAnimal}
     *
     * @param typeAnimal {@link TypeAnimal}
     * @return {@link List<Shelter>}
     */
    @Override
    public List<Shelter> findByTypeAnimal(TypeAnimal typeAnimal) {
        return shelterRepository.findByTypeOfAnimal(typeAnimal);
    }

    /***
     * Сохранение приюта
     */
    @Override
    public Shelter create(@Valid ShelterAddDto shelterDto) {
        return shelterRepository.save(shelterAddDtoMapper.toShelter(shelterDto));
    }

    /**
     * метод удаляет из БД объект {@link Shelter} по UUID
     *
     * @param id принимет UUID удаляемого объекта
     */
    @Override
    public void remove(@NotNull UUID id) {
        shelterRepository.deleteById(id);
    }

    @Override
    public Shelter update(@NotNull UUID id, @Valid ShelterAddDto shelterDto) {
        Shelter shelter = shelterRepository.findById(id).orElseThrow(); //todo обработать исключение
        Shelter newData = shelterAddDtoMapper.toShelter(shelterDto);
        shelter.setAboutShelter(newData.getAboutShelter());
        shelter.setSecurityData(newData.getSecurityData());
        shelter.setAddress(newData.getAddress());
        shelter.setWorkSchedule(newData.getWorkSchedule());
        shelter.setName(newData.getName());
        shelter.setTypeOfAnimal(newData.getTypeOfAnimal());
        shelter.setSafetyMeasures(newData.getSafetyMeasures());
        return shelter;
    }

    @Override
    public List<ShelterInfoDto> findAllDto() {
        return shelterRepository.findAll().stream()
                .map(shelterDtoMapper::toShelterDto)
                .toList();
    }

    @Override
    public ShelterInfoDto findByNameDto(@NotBlank String name) {
        return shelterDtoMapper.toShelterDto(shelterRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Нет приютов с названием " + name)));
    }

}
