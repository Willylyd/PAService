package ru.fev.accumulation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.service.CheckPositionsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckPositionsRestController.class)
public class CheckPositionRestControllerTest {

    @MockBean
    private CheckPositionsService checkPositionsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void allCheckPositions() throws Exception {
        mockMvc.perform(get("/checkposition")).andExpect(status().isOk());
    }

    @Test
    void addCP() throws Exception {
        CheckPosition checkPosition = new CheckPosition();
        String cpJson =  objectMapper.writeValueAsString(checkPosition);
        mockMvc.perform(post("/checkposition")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cpJson))
                .andExpect(status().isCreated());
    }
}
