package ru.teamfour.controller;

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
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dto.shelter.ShelterAddDto;
import ru.teamfour.dto.shelter.ShelterInfoDto;
import ru.teamfour.mappers.shelter.ShelterAddDtoMapper;
import ru.teamfour.mappers.shelter.ShelterDtoMapper;
import ru.teamfour.repositories.ShelterRepository;
import ru.teamfour.service.impl.shelter.ShelterServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static ru.teamfour.dao.entity.animal.TypeAnimal.DOG;


@SpringBootTest
@AutoConfigureMockMvc
public class ShelterControllerTestWithMockTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShelterRepository shelterRepository;


    private ShelterServiceImpl shelterService;

    @SpyBean
    ShelterAddDtoMapper shelterAddDtoMapper;
    @SpyBean
    ShelterDtoMapper shelterDtoMapper;
    @InjectMocks
    private ShelterController shelterController;

    String name = "Новый приют";
    String name1 = "Шерстяные";
    TypeAnimal typeAnimal = DOG;
    String aboutShelter = "Рассказ о приюте";
    String address = "Город, улица, дом";
    String workSchedule = "24/7";
    String safetyMeasures = "Правила поведения";
    String securityData = "Телефон охраны";
    UUID id = UUID.randomUUID();
    private final ShelterAddDto shelterAddDto1;

    private final Shelter shelter;
    private final Shelter shelter1;
    private final ShelterInfoDto shelterInfoDto;


    public ShelterControllerTestWithMockTest() {
        ShelterAddDto shelterAddDto = ShelterAddDto.builder()
                .name(name)
                .typeAnimal(typeAnimal)
                .aboutShelter(aboutShelter)
                .address(address)
                .workSchedule(workSchedule)
                .safetyMeasures(safetyMeasures)
                .securityData(securityData)
                .build();
        this.shelterAddDto1 = ShelterAddDto.builder()
                .name(name1)
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
        this.shelter1 = Shelter.builder()
                .id(id)
                .name(name1)
                .typeOfAnimal(typeAnimal)
                .aboutShelter(aboutShelter)
                .address(address)
                .workSchedule(workSchedule)
                .safetyMeasures(safetyMeasures)
                .securityData(securityData)
                .build();
        this.shelterInfoDto = ShelterInfoDto.builder()
                .name(name)
                .typeAnimal(typeAnimal)
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
        Mockito.when(this.shelterRepository
                .save(any(Shelter.class))).thenReturn(this.shelter);
        Mockito.when(this.shelterRepository.findById(any(UUID.class))).thenReturn(Optional.of(this.shelter));
        this.mockMvc.perform(MockMvcRequestBuilders.post(
                                "/shelter", new Object[0])
                        .content(shelterObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType[]{MediaType.APPLICATION_JSON}))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk()).andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.name", new Object[0]).value(this.name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aboutShelter", new Object[0]).value(this.aboutShelter))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", new Object[0]).value(this.address))
                .andExpect(MockMvcResultMatchers.jsonPath("$.workSchedule", new Object[0]).value(this.workSchedule))
                .andExpect(MockMvcResultMatchers.jsonPath("$.safetyMeasures", new Object[0]).value(this.safetyMeasures));
    }

    @Test
    public void getShelterByNameTest() throws Exception {
        Mockito.when(this.shelterDtoMapper.toShelterDto(any(Shelter.class))).thenReturn(shelterInfoDto);
        Mockito.when(this.shelterRepository.findByName(any(String.class))).thenReturn(Optional.of(this.shelter));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/shelter/name/Новый приют", new Object[0])
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType[]{MediaType.APPLICATION_JSON}))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk()).andExpect(MockMvcResultMatchers
                        .jsonPath("$.address", new Object[0]).value(this.address))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", new Object[0]).value(this.name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.aboutShelter", new Object[0])
                        .value(this.aboutShelter))
                .andExpect(MockMvcResultMatchers.jsonPath("$.workSchedule", new Object[0]).value(this.workSchedule))
                .andExpect(MockMvcResultMatchers.jsonPath("$.safetyMeasures", new Object[0]).value(this.safetyMeasures));
    }

    @Test
    public void getAllShelter() throws Exception {
        List<Shelter> list = new ArrayList<>(List.of(shelter));
        List<ShelterInfoDto> list1 = new ArrayList<>(List.of(shelterInfoDto));

        Mockito.when(this.shelterDtoMapper.toShelterDto(any(Shelter.class))).thenReturn(shelterInfoDto);
        Mockito.when(this.shelterRepository.findAll()).thenReturn(list);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/shelter/All", new Object[0])
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType[]{MediaType.APPLICATION_JSON}))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.length()").value(1));

    }


    @Test
    public void updateShelterTest() throws Exception {
        JSONObject shelterObject = new JSONObject();
        shelterObject.put("name", this.name1);
        shelterObject.put("typeAnimal", this.typeAnimal);
        shelterObject.put("aboutShelter", this.aboutShelter);
        shelterObject.put("address", this.address);
        shelterObject.put("workSchedule", this.workSchedule);
        shelterObject.put("safetyMeasures", this.safetyMeasures);
        shelterObject.put("securityData", this.securityData);
        Mockito.when(this.shelterAddDtoMapper.toShelter(shelterAddDto1)).thenReturn(shelter1);
        Mockito.when(this.shelterRepository.findById(any(UUID.class))).thenReturn(Optional.of(this.shelter));
        this.mockMvc.perform(MockMvcRequestBuilders.put("/shelter/" + id, new Object[0])
                        .content(shelterObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(new MediaType[]{MediaType.APPLICATION_JSON}))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", new Object[0])
                        .value(this.id.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", new Object[0])
                        .value(this.name1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", new Object[0])
                        .value(this.address));
    }


}


