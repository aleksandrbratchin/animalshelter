package ru.teamfour.service.impl.animal;

import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
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
     * @return возвращает созданную сущность
     */
    @Override
    public Animal create(Animal animal) {
        return repository.save(animal);
    }

    /**
     * метод заменяет сущность  {@link Animal},
     * которая уже есть в БД с таким же UUID
     */
    @Override
    public Animal put(Animal animal) {
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
    public List<Animal> findAllByType(TypeAnimal type) {
        return repository.findAnimalByTypeOfAnimal(type);
    }
}
