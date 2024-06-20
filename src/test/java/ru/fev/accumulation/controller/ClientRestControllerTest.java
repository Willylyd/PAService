package ru.fev.accumulation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.fev.accumulation.dto.ClientDto;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.mapper.ClientMapper;
import ru.fev.accumulation.service.ClientService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClientRestControllerTest {
    @Mock
    private ClientService clientService;

    @Mock
    private ClientMapper mapper;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private ClientRestController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getDiscountPointsTest() throws Exception {
        when(clientService.getDiscountPoints(any(Long.class))).thenReturn(150);

        mockMvc.perform(get("/clients/points/{id}", 3L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(150));
        verify(clientService, times(1)).getDiscountPoints(3L);
    }

    @Test
    public void getByIdTest() throws Exception {
        Client client = new Client("12345678900987654321");
        client.setDiscountPoints(145);
        ClientDto dto = new ClientDto(5L, "12345678900987654321", 145);
        when(clientService.getById(5L)).thenReturn(client);
        when(mapper.entityToDTO(client)).thenReturn(dto);

        mockMvc.perform(get("/clients/{id}", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.cardNumber").value("12345678900987654321"))
                .andExpect(jsonPath("$.discountPoints").value(145));
        verify(clientService, times(1)).getById(5L);
    }

    @Test
    public void addClientTest() throws Exception {
        Client client = new Client("88887777888877778888");
        client.setDiscountPoints(0);
        String clientJson = objectMapper.writeValueAsString(client);
        doReturn(client).when(mapper).DTOToEntity(any(ClientDto.class));
        doNothing().when(clientService).addClient(client);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isOk());
        verify(clientService, times(1)).addClient(client);
    }

    @Test
    public void getAllTest() throws Exception {
        Client c1 = new Client("11112222111122221111");
        Client c2 = new Client("22223333222233332222");
        Client c3 = new Client("33334444333344443333");
        ClientDto cd1 = new ClientDto(1L, "11112222111122221111", 0);
        ClientDto cd2 = new ClientDto(2L, "22223333222233332222", 0);
        ClientDto cd3 = new ClientDto(3L, "33334444333344443333", 0);
        doReturn(List.of(c1, c2, c3)).when(clientService).getAll();
        doReturn(List.of(cd1, cd2, cd3)).when(mapper).entitiesToDTO(any());

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void getByCardNumberTest() throws Exception {
        Client client = new Client("44444555557777733333");
        ClientDto dto = new ClientDto(4L, "44444555557777733333", 0);
        doReturn(client).when(clientService).getByCardNumber("44444555557777733333");
        doReturn(dto).when(mapper).entityToDTO(client);

        mockMvc.perform(get("/clients/cardnumber/{card_number}", "44444555557777733333"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4L))
                .andExpect(jsonPath("$.cardNumber").value("44444555557777733333"))
                .andExpect(jsonPath("$.discountPoints").value(0));
        verify(clientService, times(1)).getByCardNumber("44444555557777733333");
    }

    @Test
    public void subtractDiscountPointsTest() throws Exception {
        Client client = new Client("77778888444411110000");
        ClientDto dto = new ClientDto(15L, "77778888444411110000", 17);
        doNothing().when(clientService).subtractDiscountPoints(15L, 9);
        doReturn(client).when(clientService).getById(15L);
        doReturn(dto).when(mapper).entityToDTO(client);

        mockMvc.perform(patch("/clients/{id}", 15)
                        .param("points", String.valueOf(9)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(15L))
                .andExpect(jsonPath("$.cardNumber").value("77778888444411110000"))
                .andExpect(jsonPath("$.discountPoints").value(17));
        verify(clientService, times(1)).subtractDiscountPoints(15L, 9);
        verify(mapper, times(1)).entityToDTO(client);
    }

    @Test
    public void deleteClientTest() throws Exception {
        doNothing().when(clientService).deleteClient(any(Long.class));

        mockMvc.perform(delete("/clients/{id}", 7L))
                .andExpect(status().isNoContent());
        verify(clientService, times(1)).deleteClient(7L);
    }
}