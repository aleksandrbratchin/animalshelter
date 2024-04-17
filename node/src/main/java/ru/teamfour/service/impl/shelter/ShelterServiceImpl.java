package ru.teamfour.service.impl.shelter;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.repositories.AnimalRepository;
import ru.teamfour.repositories.ShelterRepository;
import ru.teamfour.service.api.shelter.ShelterService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ShelterServiceImpl implements ShelterService {


    private final ShelterRepository shelterRepository;
    private final AnimalRepository animalRepository;

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
    public void remove(UUID id) {
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
    public Shelter find(UUID id) {
        return shelterRepository.findById(id).orElseThrow(RuntimeException::new);//todo
    }

    /**
     * метод находит объект {@link Shelter} в БД по названию приюта
     *
     * @param name название приюта
     * @return {@link Shelter} с названием {@code name}
     */
    @Override
    public Shelter findByName(String name) {
        return shelterRepository.findByName(name).orElseThrow(RuntimeException::new);
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
    public Shelter change(UUID id, Shelter shelter) {
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
    public List<Animal> findAllAnimalsNotAdoption(UUID id) { //todo переписать на SQL
        Shelter shelter = shelterRepository.findById(id).orElseThrow(() -> new RuntimeException()); //todo написать свое
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
}
