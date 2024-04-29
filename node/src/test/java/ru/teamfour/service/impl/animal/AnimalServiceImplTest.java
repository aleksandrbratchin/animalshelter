package ru.teamfour.service.impl.animal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dto.animal.AnimalDto;
import ru.teamfour.dto.animal.AnimalUpdateDto;
import ru.teamfour.mappers.animal.AnimalMapper;
import ru.teamfour.mappers.animal.AnimalUpdateDtoMapper;
import ru.teamfour.repositories.AnimalRepository;
import ru.teamfour.service.impl.animal.AnimalServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AnimalServiceImplTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AnimalMapper animalMapper;

    @Mock
    private AnimalUpdateDtoMapper animalUpdateDtoMapper;

    @InjectMocks
    private AnimalServiceImpl animalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Animal animal = new Animal();
        when(animalRepository.findById(id)).thenReturn(Optional.of(animal));

        Animal result = animalService.findById(id);

        assertEquals(animal, result);
    }

    @Test
    void testFindById_NotFound() {
        UUID id = UUID.randomUUID();
        when(animalRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> animalService.findById(id));
    }

    @Test
    void testCreate() {
        AnimalUpdateDto animalUpdateDto = new AnimalUpdateDto();
        Animal animal = new Animal();
        AnimalDto animalDto = new AnimalDto();
        when(animalUpdateDtoMapper.toAnimal(animalUpdateDto)).thenReturn(animal);
        when(animalRepository.save(animal)).thenReturn(animal);
        when(animalMapper.toAnimalDto(animal)).thenReturn(animalDto);

        AnimalDto result = animalService.create(animalUpdateDto);

        assertEquals(animalDto, result);
    }

    @Test
    void testDelete() {
        UUID id = UUID.randomUUID();
        Animal animal = new Animal();
        AnimalDto animalDto = new AnimalDto();
        when(animalRepository.findById(id)).thenReturn(Optional.of(animal));
        doNothing().when(animalRepository).delete(animal);
        when(animalMapper.toAnimalDto(animal)).thenReturn(animalDto);

        AnimalDto result = animalService.delete(id);

        assertEquals(animalDto, result);
        verify(animalRepository, times(1)).delete(animal);
    }

    @Test
    public void testFindAllAnimal() {
        List<Animal> animals = List.of(new Animal(), new Animal());
        when(animalRepository.findAll()).thenReturn(animals);
        when(animalMapper.toAnimalDto(any())).thenReturn(new AnimalDto());

        List<AnimalDto> result = animalService.findAllAnimal();

        Assertions.assertEquals(animals.size(), result.size());
    }

    @Test
    public void testFindByAdopted() {
        List<Animal> animals = List.of(new Animal(), new Animal());
        when(animalRepository.findByAdopted(any())).thenReturn(animals);
        when(animalMapper.toAnimalDto(any())).thenReturn(new AnimalDto());

        List<AnimalDto> result = animalService.findByAdopted();

        Assertions.assertEquals(animals.size(), result.size());
    }

    @Test
    public void testFindAllByType() {
        TypeAnimal type = TypeAnimal.DOG;
        List<Animal> animals = List.of(new Animal(), new Animal());
        when(animalRepository.findAnimalByTypeAnimal(type)).thenReturn(animals);
        when(animalMapper.toAnimalDto(any())).thenReturn(new AnimalDto());

        List<AnimalDto> result = animalService.findAllByType(type);

        Assertions.assertEquals(animals.size(), result.size());
    }

    @Test
    public void testUpdate() {
        UUID id = UUID.randomUUID();
        AnimalUpdateDto animalUpdateDto = new AnimalUpdateDto();
        Animal animal = new Animal();
        when(animalRepository.findById(id)).thenReturn(Optional.of(animal));
        when(animalUpdateDtoMapper.toAnimal(animalUpdateDto)).thenReturn(animal);
        when(animalRepository.save(animal)).thenReturn(animal);
        when(animalMapper.toAnimalDto(animal)).thenReturn(new AnimalDto());

        AnimalDto result = animalService.update(id, animalUpdateDto);

        Assertions.assertNotNull(result);
    }
    }
