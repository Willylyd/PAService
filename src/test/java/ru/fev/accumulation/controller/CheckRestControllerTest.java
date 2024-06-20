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
import ru.fev.accumulation.dto.CheckDto;
import ru.fev.accumulation.dto.ClientAndCheckDTO;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.mapper.CheckMapper;
import ru.fev.accumulation.service.CheckService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CheckRestControllerTest {
    @Mock
    private CheckService checkService;

    @Mock
    private CheckMapper mapper;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private CheckRestController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void addCheckTest() throws Exception {
        Check check = new Check(71L);
        String checkJson = objectMapper.writeValueAsString(check);
        doReturn(check).when(mapper).DTOToEntity(any(CheckDto.class));
        doNothing().when(checkService).addCheck(check);

        mockMvc.perform(post("/checks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkJson))
                .andExpect(status().isOk());
        verify(checkService, times(1)).addCheck(check);
    }

    @Test
    void getByIdTest() throws Exception {
        Check check = new Check(3485L);
        CheckDto dto = new CheckDto(3L, 3485L, BigDecimal.valueOf(0));
        doReturn(check).when(checkService).getById(3L);
        doReturn(dto).when(mapper).entityToDTO(check);

        mockMvc.perform(get("/checks/{id}", 3L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.clientId").value(3485L))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(0)));
        verify(checkService, times(1)).getById(3L);
    }

    @Test
    void deleteCheckTest() throws Exception {
        doNothing().when(checkService).deleteCheck(any(Long.class));

        mockMvc.perform(delete("/checks/{id}", 61L))
                .andExpect(status().isNoContent());
        verify(checkService, times(1)).deleteCheck(61L);
    }

    @Test
    void getAllByClientIdTest() throws Exception {
        Check c1 = new Check(51L);
        Check c2 = new Check(51L);
        Check c3 = new Check(51L);
        CheckDto cd1 = new CheckDto(4L, 51L, BigDecimal.valueOf(91));
        CheckDto cd2 = new CheckDto(73L, 51L, BigDecimal.valueOf(8));
        CheckDto cd3 = new CheckDto(116L, 51L, BigDecimal.valueOf(273));
        doReturn(List.of(c1, c2, c3)).when(checkService).getAllByClientId(51L);
        doReturn(List.of(cd1, cd2, cd3)).when(mapper).entitiesToDTO(any());

        mockMvc.perform(get("/checks/client/{clientId}", 51L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
        verify(checkService, times(1)).getAllByClientId(51L);
    }

    @Test
    void getAllByCardNumberTest_notEmpty() throws Exception {
        ClientAndCheckDTO cdto1 = new ClientAndCheckDTO();
        ClientAndCheckDTO cdto2 = new ClientAndCheckDTO();
        ClientAndCheckDTO cdto3 = new ClientAndCheckDTO();
        doReturn(List.of(cdto1, cdto2, cdto3)).when(checkService).getAllByCardNumber("11112222111122221111");

        mockMvc.perform(get("/checks/cardnumber/{cardNumber}", "11112222111122221111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
        verify(checkService, times(1)).getAllByCardNumber("11112222111122221111");
    }

    @Test
    void getAllByCardNumberTest_Empty() throws Exception {
        doReturn(Collections.emptyList()).when(checkService).getAllByCardNumber("44444444444444444444");

        mockMvc.perform(get("/checks/cardnumber/{cardNumber}", "44444444444444444444"))
                .andExpect(status().isNotFound());
        verify(checkService, times(1)).getAllByCardNumber("44444444444444444444");
    }

    @Test
    void getAll() throws Exception {
        Check c1 = new Check(354L);
        Check c2 = new Check(68L);
        Check c3 = new Check(8273L);
        CheckDto cd1 = new CheckDto(4L, 354L, BigDecimal.valueOf(91));
        CheckDto cd2 = new CheckDto(73L, 68L, BigDecimal.valueOf(8));
        CheckDto cd3 = new CheckDto(116L, 8273L, BigDecimal.valueOf(273));
        doReturn(List.of(c1, c2, c3)).when(checkService).getAll();
        doReturn(List.of(cd1, cd2, cd3)).when(mapper).entitiesToDTO(any());

        mockMvc.perform(get("/checks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
        verify(checkService, times(1)).getAll();
    }
}