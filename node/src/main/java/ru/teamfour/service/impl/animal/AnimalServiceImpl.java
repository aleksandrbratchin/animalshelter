package ru.teamfour.service.impl.animal;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.repositories.AnimalRepository;
import ru.teamfour.service.api.animal.AnimalService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Validated
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository repository;

    public AnimalServiceImpl(AnimalRepository repository) {
        this.repository = repository;
    }

    /**
     * метод создает сущность  {@link Animal}  и сохраняет ее в БД
     *
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
    public void delete(@NotNull UUID id) {
        repository.deleteById(id);

    }

    /**
     * метод находит сущность {@link Animal} по UUID
     *
     * @param id тип UUID
     */
    @Override
    public Animal findById(@NotNull UUID id) {
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
     * метод для поиска животных по статусу
     */
    @Override
    public List<Animal> findByAdopted(AdoptionAnimalState adoptionAnimalState) {
        return repository.findByAdopted(adoptionAnimalState);
    }

    /**
     * @param type тип животного, например: DOG
     * @return возвращает список животных по указанному типу
     */
    @Override
    public List<Animal> findAllByType(TypeAnimal type) {
        return repository.findAnimalByTypeAnimal(type);
    }

}
