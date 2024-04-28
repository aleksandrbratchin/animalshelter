package ru.teamfour.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalInfoDto;
import ru.teamfour.service.impl.adoptionanimal.AdoptionProcessAnimalService;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdoptionAnimalsController.class)
@AutoConfigureMockMvc
class AdoptionAnimalsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdoptionProcessAnimalService adoptionAnimalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAdoptionAnimal() throws Exception {
        UUID adoptionAnimalId = UUID.randomUUID();
        AdoptionProcessAnimal adoptionProcessAnimal = new AdoptionProcessAnimal();
        Mockito.when(adoptionAnimalService.findById(adoptionAnimalId)).thenReturn(adoptionProcessAnimal);

        mockMvc.perform(get("/adoptionanimal/{adoptionAnimalId}", adoptionAnimalId))
                .andExpect(status().isOk());

        verify(adoptionAnimalService, times(1)).findById(adoptionAnimalId);
    }

    @Test
    void addfourteendays() throws Exception {
        UUID adoptionAnimalId = UUID.randomUUID();
        AdoptionProcessAnimalInfoDto adoptionProcessAnimalInfoDto = new AdoptionProcessAnimalInfoDto();
        Mockito.when(adoptionAnimalService.addfourteendays(adoptionAnimalId)).thenReturn(adoptionProcessAnimalInfoDto);

        mockMvc.perform(get("/adoptionanimal/addfourteendays/{adoptionAnimalId}", adoptionAnimalId))
                .andExpect(status().isOk());

        verify(adoptionAnimalService, times(1)).addfourteendays(adoptionAnimalId);
    }

    @Test
    void addthirtydays() throws Exception {
        UUID adoptionAnimalId = UUID.randomUUID();
        AdoptionProcessAnimalInfoDto adoptionProcessAnimalInfoDto = new AdoptionProcessAnimalInfoDto();
        Mockito.when(adoptionAnimalService.addthirtydays(adoptionAnimalId)).thenReturn(adoptionProcessAnimalInfoDto);

        mockMvc.perform(get("/adoptionanimal/addthirtydays/{adoptionAnimalId}", adoptionAnimalId))
                .andExpect(status().isOk());

        verify(adoptionAnimalService, times(1)).addthirtydays(adoptionAnimalId);
    }

    @Test
    void approved() throws Exception {
        UUID adoptionAnimalId = UUID.randomUUID();
        AdoptionProcessAnimalInfoDto adoptionProcessAnimalInfoDto = new AdoptionProcessAnimalInfoDto();
        Mockito.when(adoptionAnimalService.approved(adoptionAnimalId)).thenReturn(adoptionProcessAnimalInfoDto);

        mockMvc.perform(get("/adoptionanimal/approved/{adoptionAnimalId}", adoptionAnimalId))
                .andExpect(status().isOk());

        verify(adoptionAnimalService, times(1)).approved(adoptionAnimalId);
    }

    @Test
    void rejected() throws Exception {
        UUID adoptionAnimalId = UUID.randomUUID();
        AdoptionProcessAnimalInfoDto adoptionProcessAnimalInfoDto = new AdoptionProcessAnimalInfoDto();
        Mockito.when(adoptionAnimalService.rejected(adoptionAnimalId)).thenReturn(adoptionProcessAnimalInfoDto);

        mockMvc.perform(get("/adoptionanimal/rejected/{adoptionAnimalId}", adoptionAnimalId))
                .andExpect(status().isOk());

        verify(adoptionAnimalService, times(1)).rejected(adoptionAnimalId);
    }

    @Test
    void findAllActiveAdoptions() throws Exception {
        mockMvc.perform(get("/adoptionanimal/findAllActiveAdoptions"))
                .andExpect(status().isOk());

        verify(adoptionAnimalService, times(1)).receiveAdoptionsOnWhichADecisionNeedsToBeMade();
    }
}
