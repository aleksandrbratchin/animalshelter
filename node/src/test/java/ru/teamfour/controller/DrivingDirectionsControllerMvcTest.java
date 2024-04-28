package ru.teamfour.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.teamfour.dao.entity.drivingdirections.DrivingDirections;
import ru.teamfour.service.impl.drivingdirections.DrivingDirectionsServiceImpl;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(DrivingDirectionsController.class)
public class DrivingDirectionsControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DrivingDirectionsServiceImpl drivingDirectionsService;

    @Test
    public void testUploadDirections() throws Exception {
        UUID shelterId = UUID.randomUUID();
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/drivingDirections/addDirections/{shelterId}", shelterId)
                        .file(file))
                .andExpect(status().isOk());

        Mockito.verify(drivingDirectionsService).createDrivingDirections(shelterId, file);
    }

    @Test
    public void testDownloadDrivingDirections() throws Exception {
        UUID id = UUID.randomUUID();
        DrivingDirections drivingDirections = new DrivingDirections();
        drivingDirections.setData(new byte[]{1, 2, 3});
        Mockito.when(drivingDirectionsService.findByShelterId(id)).thenReturn(drivingDirections);

        mockMvc.perform(MockMvcRequestBuilders.get("/drivingDirections/{idShelter}/getDirections", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(drivingDirections.getData()));
    }

    @Test
    public void testDelete() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.delete("/drivingDirections/{idShelter}/deleteDirections", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(drivingDirectionsService).deleteDrivingDirections(id);
    }

    @Test
    public void testPutDrivingDirections() throws Exception {

        UUID id = UUID.randomUUID();
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/drivingDirections/" + id)
                        .file(file))
                .andExpect(status().isOk());

        Mockito.verify(drivingDirectionsService).put(id, file);
    }
}

