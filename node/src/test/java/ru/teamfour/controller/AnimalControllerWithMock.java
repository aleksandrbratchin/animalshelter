package ru.teamfour.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dto.animal.AnimalDto;
import ru.teamfour.dto.animal.AnimalUpdateDto;
import ru.teamfour.mappers.animal.AnimalDtoMapper;
import ru.teamfour.mappers.animal.AnimalMapper;
import ru.teamfour.mappers.animal.AnimalUpdateDtoMapper;
import ru.teamfour.repositories.AnimalRepository;
import ru.teamfour.repositories.ShelterRepository;
import ru.teamfour.service.impl.animal.AnimalServiceImpl;
import ru.teamfour.validators.adoptionanimalstate.AdoptionAnimalStateValid;
import ru.teamfour.validators.typeanimal.TypeAnimalValid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static ru.teamfour.dao.entity.animal.AdoptionAnimalState.ADOPTED;
import static ru.teamfour.dao.entity.animal.AdoptionAnimalState.NOT_ADOPTED;
import static ru.teamfour.dao.entity.animal.TypeAnimal.DOG;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalControllerWithMock {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnimalRepository animalRepository;
    private AnimalServiceImpl animalService;
    AnimalDtoMapper animalDtoMapper;
    private AnimalUpdateDto animalUpdateDto1;
    private AnimalUpdateDto animalUpdateDto2;
    private AnimalMapper animalMapper;
    private AnimalUpdateDtoMapper animalUpdateDtoMapper;
    private Animal animal1;
    private Animal animal2;
    private AnimalDto animalDto1;
    @InjectMocks
    private AnimalController animalController;
    String name1 = "NAME_1";
    String name2 = "NAME_2";
    String breed1 = "BREED_1";
    Double age1 = 2.0;
    String habits1 = "HABITS_1";
    UUID id1 = UUID.randomUUID();
    UUID id2 = UUID.randomUUID();
    UUID idShelter = UUID.randomUUID();

    public AnimalControllerWithMock() {
        this.animalUpdateDto1 = AnimalUpdateDto.builder()
                .name(name1)
                .typeAnimal(DOG)
                .age(age1)
                .breed(breed1)
                .habits(habits1)
                .adopted(NOT_ADOPTED)
                .idShelter(id1)
                .build();
        this.animalUpdateDto2 = AnimalUpdateDto.builder()
                .name(name1)
                .typeAnimal(DOG)
                .age(age1)
                .breed(breed1)
                .habits(habits1)
                .adopted(NOT_ADOPTED)
                .idShelter(id1)
                .build();

        this.animal1 = Animal.builder()
                .id(id1)
                .name(name1)
                .typeAnimal(DOG)
                .age(age1)
                .breed(breed1)
                .habits(habits1)
                .adopted(NOT_ADOPTED)
                .build();
        this.animal2 = Animal.builder()
                .id(id2)
                .name(name2)
                .typeAnimal(DOG)
                .age(age1)
                .breed(breed1)
                .habits(habits1)
                .adopted(ADOPTED)
                .build();

    }

    @Test
    public void saveAnimalTest() throws Exception {
        JSONObject animalObject = new JSONObject();
        animalObject.put("name", this.name1);
        animalObject.put("breed", this.breed1);
        animalObject.put("typeAnimal", DOG);
        animalObject.put("age", age1);
        animalObject.put("habits", habits1);
        animalObject.put("adopted", NOT_ADOPTED);
        animalObject.put("idShelter", idShelter.toString());

        Mockito.when(this.animalRepository
                .save(any(Animal.class))).thenReturn(this.animal1);
        Mockito.when(this.animalRepository.findById(any(UUID.class))).thenReturn(Optional.of(this.animal1));
        this.mockMvc.perform(MockMvcRequestBuilders.post(
                                "/animal", new Object[0])
                        .content(animalObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType[]{MediaType.APPLICATION_JSON}))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", new Object[0]).value(this.name1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.breed", new Object[0]).value(this.breed1));

    }

    @Test
    public void putAnimalTest() throws Exception {
        JSONObject animalObject = new JSONObject();

        animalObject.put("name", this.name2);
        animalObject.put("breed", this.breed1);
        animalObject.put("typeAnimal", DOG);
        animalObject.put("age", age1);
        animalObject.put("habits", habits1);
        animalObject.put("adopted", NOT_ADOPTED);
        animalObject.put("idShelter", idShelter.toString());

        Mockito.when(this.animalRepository.findById(any(UUID.class))).thenReturn(Optional.of(this.animal1));
        Mockito.when(this.animalRepository
                .save(any(Animal.class))).thenReturn(this.animal1);
        this.mockMvc.perform(MockMvcRequestBuilders.put(
                                "/animal/" + id1, new Object[0])
                        .content(animalObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType[]{MediaType.APPLICATION_JSON}))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", new Object[0])
                        .value(this.id1.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", new Object[0]).value(this.name2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.breed", new Object[0]).value(this.breed1));

    }

    @Test
    public void getAllTest() throws Exception {
        List<Animal> list = new ArrayList<>(List.of(animal1, animal2));
        Mockito.when(this.animalRepository.findAll()).thenReturn(list);
        this.mockMvc.perform(MockMvcRequestBuilders.get(
                                "/animal/All", new Object[0])
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType[]{MediaType.APPLICATION_JSON}))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.length()").value(2));

    }

    @Test
    public void getAllByAdoptedTest() throws Exception {
        List<Animal> list = new ArrayList<>(List.of(animal2));
        Mockito.when(this.animalRepository.findByAdopted(ADOPTED)).thenReturn(list);
        this.mockMvc.perform(MockMvcRequestBuilders.get(
                                "/animal/nameAdoption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.length()").value(1));

    }

    @Test
    public void getAllByTypeTest() throws Exception {
        List<Animal> list = new ArrayList<>(List.of(animal1, animal2));
        Mockito.when(this.animalRepository.findAnimalByTypeAnimal(any(TypeAnimal.class))).thenReturn(list);
        this.mockMvc.perform(MockMvcRequestBuilders.get(
                                "/animal/AllType/" + DOG, new Object[0])
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.length()").value(2));

    }
}
