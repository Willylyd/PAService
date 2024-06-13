package ru.fev.accumulation.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.mapper.ClientMapper;
import ru.fev.accumulation.repository.ClientRepository;
import ru.fev.accumulation.service.ClientService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientRestControllerTest {

    @Mock
    ClientService clientService;

    @Mock
    ClientMapper clientMapper;

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientRestController clientRestController;

    @Test
    void getAllClients_ReturnsResponseEntityWithListOfDto() {
        // given
        var clients = List.of(new Client("11111111111111111111"),
                new Client("22222222222222222222"));
        doReturn(clients).when(this.clientService).getAll();

        // when
        var responseEntity = this.clientRestController.getAll();

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(this.clientMapper.entitiesToDTO(clients), responseEntity.getBody());
    }

//    @Test
//    void addClient_RequestIsValid_ReturnsResponseEntityWithDto() {
//        // given
//        var client = new Client("33333333333333333333");
//        BindingResult br = mock(BindingResult.class);
//
//        // when
//        var responseEntity = this.clientRestController.addClient(this.clientMapper.entityToDTO(client), br);
//
//        // then
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//
//        if (responseEntity.getBody() != null) { // can't replace this check with assert
//            assertEquals(responseEntity.getBody().getCardNumber(), client.getCardNumber());
//
//            verify(this.clientRepository).save(client);
//            verifyNoMoreInteractions(this.clientRepository);
//        }
//    }

    @Test
    void getById_RequestIsValid_ReturnsResponseEntityWithDto() {
        // given
        var client = new Client("44444444444444444444");
        doReturn(client).when(this.clientService).getById(1L);

        // when
        var responseEntity = this.clientRestController.getById(1L);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(this.clientMapper.entityToDTO(client), responseEntity.getBody());
    }

    @Test
    void getDiscountPoints_RequestIsValid_ReturnsInteger() {
        // given
        doReturn(150).when(this.clientService).getDiscountPoints(5L);

        // when
        var responseEntity = this.clientRestController.getDiscountPoints(5L);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(150, responseEntity.getBody());
    }

    @Test
    void getByCardNumber_RequestIsValid_ReturnResponseEntityWithDto() {
        // given
        var client = new Client("66666666666666666666");
        doReturn(client).when(this.clientService).getByCardNumber("66666666666666666666");

        // when
        var responseEntity = this.clientRestController.getByCardNumber("66666666666666666666");

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(this.clientMapper.entityToDTO(client), responseEntity.getBody());
    }
}