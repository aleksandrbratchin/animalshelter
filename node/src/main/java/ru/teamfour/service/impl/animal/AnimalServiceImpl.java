package ru.teamfour.service.impl.animal;

import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.repositories.AnimalRepository;
import ru.teamfour.service.api.animal.AnimalService;

import java.util.List;
import java.util.UUID;

@Service
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository repository;

    public AnimalServiceImpl(AnimalRepository repository) {
        this.repository = repository;
    }

    /**
     * метод создает сущность  {@link Animal}  и сохраняет ее в БД
     *
     * @param id        тип UUID, генерируется автоматически
     * @param type      вид животного
     * @param name      кличка
     * @param age       возраст
     * @param breed     порода
     * @param habits    привычки
     * @param adopted   true - усыновленное, false - неусыновленное
     * @param idShelter id приюта, в котором будет находиться животное
     * @return возвращает созданную сущность
     */
    @Override
    public Animal create(UUID id, String type, String name, Double age,
                         String breed, String habits,
                         boolean adopted, UUID idShelter) {
        Animal animal = new Animal(id, type, name, age, breed,
                habits, adopted, idShelter);

        return repository.save(animal);
    }

    /**
     * метод заменяет сущность  {@link Animal},
     * которая уже есть в БД с таким же UUID
     *
     * @param id        тип UUID
     * @param type      вид животного
     * @param name      кличка
     * @param age       возраст
     * @param breed     порода
     * @param habits    привычки
     * @param adopted   true - усыновленное, false - неусыновленное
     * @param idShelter id приюта, в котором будет находиться животное
     * @return возвращает созданную сущность
     */
    @Override
    public Animal put(UUID id, String type, String name, Double age,
                      String breed, String habits, boolean adopted,
                      UUID idShelter) {
        Animal animal = new Animal(id, type, name, age, breed,
                habits, adopted, idShelter);

        return repository.save(animal);
    }

    /**
     * метод удаляет сущность {@link Animal} по UUID
     *
     * @param id тип UUID
     */
    @Override
    public void delete(UUID id) {
        repository.deleteById(id);

    }

    /**
     * метод находит сущность {@link Animal} по UUID
     *
     * @param id тип UUID
     */
    @Override
    public Animal findById(UUID id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * @return возвращает список всех животных
     */
    @Override
    public List<Animal> findAll() {
        return repository.findAll();
    }

    /**
     * метод по поиску усыновленных или неусыновленных животных
     *
     * @param b true или false
     * @return возвращает список всех животных усыновленных или неусыновленных
     */
    @Override
    public List<Animal> findAllByAdopted(boolean b) {
        return repository.findAnimalByAdopted(b);
    }

    /**
     * @param type тип животного, например: собака
     * @return возвращает список животных по указанному типу
     */
    @Override
    public List<Animal> findAllByType(String type) {
        return repository.findAnimalByTypeOfAnimalIgnoreCase(type);
    }
}
