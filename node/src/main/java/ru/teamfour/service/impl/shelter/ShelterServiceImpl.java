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


    private final ShelterRepository shelterRepository;
    private final ShelterAddDtoMapper shelterAddDtoMapper;
    private final ShelterDtoMapper shelterDtoMapper;

    /**
     * метод добавляет в БД объект {@link Shelter}
     *
     * @param shelter метод принимает в качестве параметра объект Shelter
     * @return метод возвращает объект, записанный в БД
     */
    @Override
    public Shelter add(Shelter shelter) {

        return shelterRepository.save(shelter);
    }

    /**
     * Метод создает объект класса {@link Shelter}
     * по введенным параметрам
     *
     * @param name
     * @param typeAnimal
     * @param aboutShelter
     * @param address
     * @param safetyMeasures
     * @param securityData
     * @param workSchedule
     * @return возвращает созданный объект
     */

    @Override
    public Shelter add(String name,
                       TypeAnimal typeAnimal,
                       String aboutShelter,
                       String address,
                       String safetyMeasures,
                       String securityData,
                       String workSchedule) {

        Shelter shelter = Shelter.builder()
                .name(name)
                .typeOfAnimal(typeAnimal)
                .aboutShelter(aboutShelter)
                .address(address)
                .workSchedule(workSchedule)
                .safetyMeasures(safetyMeasures)
                .securityData(securityData).build();
        return shelterRepository.save(shelter);
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

    /**
     * метод удаляет из БД объект {@link Shelter} по имени
     *
     * @param name принимет имя удаляемого объекта
     */

    @Override
    public void removeByName(String name) {
        // UUID idShelter = findByName(name).getId();
        shelterRepository.deleteByName(name);

    }

    /**
     * метод находит объект {@link Shelter} в БД по UUID
     *
     * @param id UUID
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
     * метод заменяет объект в БД на переданный в параметре
     *
     * @param id      тип UUID
     * @param shelter объект класса {@link Shelter}
     * @return возвращает переданный объект
     */
    @Override
    public Shelter change(@NotNull UUID id, Shelter shelter) {
        return shelterRepository.save(shelter);

    }

    /**
     * метод меняет значение поля aboutShelter в сущности {@link Shelter}
     *
     * @param name
     * @param aboutShelter
     * @return возвращает обновленную сущность
     */
    @Override
    public Shelter changeAboutShelter(String name,
                                      String aboutShelter) {

        Shelter shelter = findByName(name);
        shelter.setAboutShelter(aboutShelter);

        return shelterRepository.save(shelter);
    }

    /**
     * метод меняет значение поля typeOfAnimal в сущности {@link Shelter}
     *
     * @param name
     * @param typeAnimal
     * @return возвращает обновленную сущность
     */
    @Override
    public Shelter changeTypeAnimal(String name,
                                    TypeAnimal typeAnimal) {

        Shelter shelter = findByName(name);
        shelter.setTypeOfAnimal(typeAnimal);

        return shelterRepository.save(shelter);
    }

    /**
     * метод меняет значение поля workSchedule в сущности {@link Shelter}
     *
     * @param name
     * @param work
     * @return возвращает обновленную сущность
     */
    @Override
    public Shelter changeWorkSchedule(String name,
                                      String work) {

        Shelter shelter = findByName(name);
        shelter.setWorkSchedule(work);

        return shelterRepository.save(shelter);
    }

    /**
     * метод меняет значение поля securityData в сущности {@link Shelter}
     *
     * @param name
     * @param security
     * @return возвращает обновленную сущность
     */
    @Override
    public Shelter changeSecurity(String name,
                                  String security) {

        Shelter shelter = findByName(name);

        shelter.setSecurityData(security);

        return shelterRepository.save(shelter);
    }

    /**
     * метод меняет значение поля address в сущности {@link Shelter}
     *
     * @param name
     * @param address
     * @return возвращает обновленную сущность
     */
    @Override
    public Shelter changeAddress(String name,
                                 String address) {

        Shelter shelter = findByName(name);

        shelter.setAddress(address);

        return shelterRepository.save(shelter);
    }

    /**
     * метод меняет значение поля safetyMeasures в сущности {@link Shelter}
     *
     * @param name
     * @param safety
     * @return возвращает обновленную сущность
     */
    @Override
    public Shelter changeSafetyMeasures(String name,
                                        String safety) {

        Shelter shelter = findByName(name);

        shelter.setSafetyMeasures(safety);

        return shelterRepository.save(shelter);
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
     * @param {@link TypeAnimal}
     * @return {@link List<Shelter>}
     */
    @Override
    public List<Shelter> findByTypeAnimal(TypeAnimal typeAnimal) {
        return shelterRepository.findByTypeOfAnimal(typeAnimal);
    }

    /***
     * Методы Александра
     */
    @Override
    public Shelter create(@Valid ShelterAddDto shelterDto) {
        return shelterRepository.save(shelterAddDtoMapper.toShelter(shelterDto));
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
