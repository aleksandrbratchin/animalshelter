package ru.teamfour.controller;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dto.shelter.ShelterAddDto;
import ru.teamfour.mappers.shelter.ShelterAddDtoMapper;
import ru.teamfour.repositories.ShelterRepository;
import ru.teamfour.service.api.shelter.ShelterService;
import ru.teamfour.service.impl.shelter.ShelterServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.spy;
import static ru.teamfour.dao.entity.animal.TypeAnimal.DOG;

//@WebMvcTest({ShelterController.class})
@SpringBootTest
@AutoConfigureMockMvc
public class ShelterControllerTestWithMock {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShelterRepository shelterRepository;

    @SpyBean
    private ShelterServiceImpl shelterService;//=spy(ShelterServiceImpl.class);

    @SpyBean
    ShelterAddDtoMapper shelterAddDtoMapper;
    @InjectMocks
    private ShelterController shelterController;

    String name = "Новый приют";
    TypeAnimal typeAnimal = DOG;
    String aboutShelter = "Рассказ о приюте";
    String address = "Город, улица, дом";
    String workSchedule = "24/7";
    String safetyMeasures = "Правила поведения";
    String securityData = "Телефон охраны";
    UUID id = UUID.randomUUID();
    ShelterAddDto shelterAddDto;
    Shelter shelter;


    public ShelterControllerTestWithMock() {
        this.shelterAddDto = ShelterAddDto.builder()
                .name(name)
                .typeAnimal(typeAnimal)
                .aboutShelter(aboutShelter)
                .address(address)
                .workSchedule(workSchedule)
                .safetyMeasures(safetyMeasures)
                .securityData(securityData)
                .build();
        this.shelter = Shelter.builder()
                .id(id)
                .name(name)
                .typeOfAnimal(typeAnimal)
                .aboutShelter(aboutShelter)
                .address(address)
                .workSchedule(workSchedule)
                .safetyMeasures(safetyMeasures)
                .securityData(securityData)
                .build();

    }

    @Test
    public void saveShelterTest() throws Exception {
        JSONObject shelterObject = new JSONObject();
        shelterObject.put("name", this.name);
        shelterObject.put("typeAnimal", this.typeAnimal);
        shelterObject.put("aboutShelter", this.aboutShelter);
        shelterObject.put("address", this.address);
        shelterObject.put("workSchedule", this.workSchedule);
        shelterObject.put("safetyMeasures", this.safetyMeasures);
        shelterObject.put("securityData", this.securityData);
        //   Mockito.when(this.shelterAddDtoMapper.toShelter(shelterAddDto)).thenReturn(shelter);
        Mockito.when(this.shelterRepository
                .save(ArgumentMatchers.any(Shelter.class))).thenReturn(this.shelter);
        Mockito.when(this.shelterRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(this.shelter));
        this.mockMvc.perform(MockMvcRequestBuilders.post(
                                "/shelter", new Object[0])
                        .content(shelterObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType[]{MediaType.APPLICATION_JSON}))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk()).andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.name", new Object[0]).value(this.name))
                //  .andExpect(MockMvcResultMatchers.jsonPath("$.typeOfAnimal", new Object[0]).value(this.typeAnimal))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aboutShelter", new Object[0]).value(this.aboutShelter));
    }

}


