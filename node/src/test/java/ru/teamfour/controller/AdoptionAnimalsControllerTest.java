package ru.teamfour.controller;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalCreateDto;
import ru.teamfour.repositories.AdoptionProcessAnimalRepository;
import ru.teamfour.repositories.AnimalRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static ru.teamfour.dao.entity.animal.AdoptionAnimalState.NOT_ADOPTED;
import static ru.teamfour.dao.entity.animal.TypeAnimal.DOG;

@SpringBootTest
@AutoConfigureMockMvc
public class AdoptionAnimalsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AdoptionProcessAnimalRepository adoptionProcessAnimalRepository;
    @InjectMocks
    private AdoptionAnimalsController adoptionAnimalsController;
    UUID idUser = UUID.randomUUID();
    UUID idShelter = UUID.randomUUID();
    UUID idAnimal = UUID.randomUUID();
    UUID idProcess = UUID.randomUUID();
    User user = User.builder()
            .id(idUser)
            .build();
    Shelter shelter = Shelter.builder()
            .id(idShelter)
            .build();
    Animal animal = Animal.builder()
            .id(idAnimal)
            .build();
    AdoptionProcessAnimal process = AdoptionProcessAnimal.builder()
            .id(idProcess)
            .user(user)
            .shelter(shelter)
            .animal(animal)
            .build();
    AdoptionProcessAnimalCreateDto adoptionProcessAnimalCreateDto =
            AdoptionProcessAnimalCreateDto.builder()
                    .idAnimal(idAnimal)
                    .idUser(idUser)
                    .idShelter(idShelter)
                    .build();

    @Test
    public void getAdoptionAnimalTest() throws Exception {

        Mockito.when(this.adoptionProcessAnimalRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(process));
        this.mockMvc.perform(MockMvcRequestBuilders.get(
                                "/adoptionanimal/" + idAnimal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType[]{MediaType.APPLICATION_JSON}))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())

                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id").value(idProcess.toString()));

    }

    @Test
    public void addTest() throws Exception {
        JSONObject animalObject = new JSONObject();
        animalObject.put("user", this.user);
        animalObject.put("shelter", this.shelter);
        animalObject.put("animal", this.animal);

        Mockito.when(this.adoptionProcessAnimalRepository
                .save(any(AdoptionProcessAnimal.class))).thenReturn(process);

        this.mockMvc.perform(MockMvcRequestBuilders.post(
                                "/adoptionanimal")
                        .content(animalObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType[]{MediaType.APPLICATION_JSON}))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
              /*  .andExpect(MockMvcResultMatchers.jsonPath("$.name", new Object[0]).value(this.name1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.breed", new Object[0]).value(this.breed1));

    }*/
    }

}
