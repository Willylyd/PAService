package ru.fev.accumulation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.fev.accumulation.dto.CheckPositionDto;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.mapper.CheckPositionMapper;
import ru.fev.accumulation.service.CheckPositionsService;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CheckPositionsRestControllerTest {
    @Mock
    private CheckPositionsService checkPositionsService;

    @Mock
    private CheckPositionMapper mapper;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private CheckPositionsRestController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getByIdTest() throws Exception {
        CheckPosition checkPosition = new CheckPosition(4L, BigDecimal.valueOf(55));
        CheckPositionDto dto = new CheckPositionDto(3L, 4L, BigDecimal.valueOf(55));
        doReturn(checkPosition).when(checkPositionsService).getById(3L);
        doReturn(dto).when(mapper).entityToDTO(checkPosition);

        mockMvc.perform(get("/checkposition/{id}", 3L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.checkId").value(4L))
                .andExpect(jsonPath("$.posAmount").value(BigDecimal.valueOf(55)));
        verify(checkPositionsService, times(1)).getById(3L);

    }

    @Test
    void getAllByCheckIdTest() throws Exception {
        CheckPosition cp1 = new CheckPosition(38L, BigDecimal.valueOf(81));
        CheckPosition cp2 = new CheckPosition(38L, BigDecimal.valueOf(711));
        CheckPosition cp3 = new CheckPosition(38L, BigDecimal.valueOf(643));
        CheckPositionDto cpd1 = new CheckPositionDto(3L, 38L, BigDecimal.valueOf(81));
        CheckPositionDto cpd2 = new CheckPositionDto(6L, 38L, BigDecimal.valueOf(711));
        CheckPositionDto cpd3 = new CheckPositionDto(11L, 38L, BigDecimal.valueOf(643));
        doReturn(List.of(cp1, cp2, cp3)).when(checkPositionsService).getAllByCheckId(38L);
        doReturn(List.of(cpd1, cpd2, cpd3)).when(mapper).entitiesToDTO(any());

        mockMvc.perform(get("/checkposition/check/{checkId}", 38L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
        verify(checkPositionsService, times(1)).getAllByCheckId(38L);
    }

    @Test
    void addCheckPositionTest() throws Exception {
        CheckPosition cp = new CheckPosition(97L, BigDecimal.valueOf(555));
        String checkPositionJson = objectMapper.writeValueAsString(cp);
        doReturn(cp).when(mapper).DTOToEntity(any(CheckPositionDto.class));
        doNothing().when(checkPositionsService).addCheckPosition(cp);

        mockMvc.perform(post("/checkposition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkPositionJson))
                .andExpect(status().isOk());
        verify(checkPositionsService, times(1)).addCheckPosition(cp);

    }

    @Test
    void getAllTest() throws Exception {
        CheckPosition cp1 = new CheckPosition(38L, BigDecimal.valueOf(81));
        CheckPosition cp2 = new CheckPosition(6L, BigDecimal.valueOf(711));
        CheckPosition cp3 = new CheckPosition(377L, BigDecimal.valueOf(643));
        CheckPositionDto cpd1 = new CheckPositionDto(3L, 38L, BigDecimal.valueOf(81));
        CheckPositionDto cpd2 = new CheckPositionDto(6L, 6L, BigDecimal.valueOf(711));
        CheckPositionDto cpd3 = new CheckPositionDto(11L, 377L, BigDecimal.valueOf(643));
        doReturn(List.of(cp1, cp2, cp3)).when(checkPositionsService).getAll();
        doReturn(List.of(cpd1, cpd2, cpd3)).when(mapper).entitiesToDTO(any());

        mockMvc.perform(get("/checkposition"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
        verify(checkPositionsService, times(1)).getAll();
    }

    @Test
    void deleteCheckPositionTest() throws Exception {
        doNothing().when(checkPositionsService).deleteCheckPosition(71L);

        mockMvc.perform(delete("/checkposition/{id}", 71L))
                .andExpect(status().isNoContent());
        verify(checkPositionsService, times(1)).deleteCheckPosition(71L);
    }
}