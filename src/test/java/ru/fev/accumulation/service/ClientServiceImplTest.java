package ru.fev.accumulation.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.exceptions.PAIllegalIdException;
import ru.fev.accumulation.exceptions.PAIncorrectArgumentException;
import ru.fev.accumulation.repository.ClientRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void testAddClient_ValidCardNumber_ThrowsNothing() {
        // given
        Client validClient = new Client("11112222333344445555");
        doReturn(List.of(validClient)).when(clientRepository).findAll();

        // when
        clientService.addClient(validClient);

        // then
        assertEquals(1, clientService.getAll().size());
        Mockito.verify(clientRepository, times(1)).save(validClient);
    }

    @Test
    public void testAddClient_InvalidCardNumber_ThrowsException() {
        // given
        Client invalidClient = new Client("1111222233334444");

        // when
        var exception = assertThrows(PAIncorrectArgumentException.class, () -> {
            clientService.addClient(invalidClient);
        });

        // then
        assertEquals("Incorrect card number", exception.getMessage());
        Mockito.verify(clientRepository, never()).save(invalidClient);
    }

    @Test
    public void getById_validId_ThrowsNothing() {
        // given
        Client client = new Client("11112222333344445555");
        doReturn(client).when(clientRepository).getReferenceById(1L);

        // when
        var res = clientService.getById(1L);

        // then
        assertEquals(client.getCardNumber(), res.getCardNumber());
        Mockito.verify(clientRepository, times(1)).getReferenceById(1L);
    }

    @Test
    public void getById_invalidId_ThrowsException() {
        // given
        Long id = -3L;

        // then
        assertThrows(PAIllegalIdException.class, () -> clientService.getById(id));
        Mockito.verify(clientRepository, never()).getReferenceById(1L);
    }


    @Test
    public void getByCardNumber_validCardId_ThrowsNothing() {
        // given
        Client client = new Client("55556666777788889999");
        doReturn(client).when(clientRepository).getByCardNumber(client.getCardNumber());

        // when
        var res = clientService.getByCardNumber(client.getCardNumber());

        // then
        assertEquals(client.getCardNumber(), res.getCardNumber());
        Mockito.verify(clientRepository, times(1)).getByCardNumber(client.getCardNumber());
    }

    @Test
    public void getByCardNumber_invalidCardId_ThrowsException() {
        // given
        String invalidCardNum = "1234567890";

        // then
        Mockito.verify(clientRepository, never()).getByCardNumber(invalidCardNum);
        assertThrows(PAIncorrectArgumentException.class, () -> clientService.getByCardNumber(invalidCardNum));
    }

    @Test
    public void getDiscountPoints_validId_throwsNothing() {
        // given
        Client client = new Client("11112222333344445555");
        client.setDiscountPoints(541);
        client.setId(1L);
        doReturn(client).when(clientRepository).getReferenceById(1L);

        // when
        var points = clientService.getDiscountPoints(1L);

        // then
        assertEquals(541, points);
        Mockito.verify(clientRepository, times(1)).getReferenceById(1L);
    }

    @Test
    public void getDiscountPoints_invalidId_throwsException() {
        // given
        Long invalidId = -6L;

        // then
        assertThrows(PAIllegalIdException.class, () -> clientService.getDiscountPoints(invalidId));
        Mockito.verify(clientRepository, never()).getReferenceById(invalidId);
    }


    @Test
    public void deleteClient_validId_throwsNothing() {
        // given
        Long id = 3L;
        doNothing().when(clientRepository).deleteById(id);

        // when
        clientService.deleteClient(id);

        // then
        Mockito.verify(clientRepository, times(1)).deleteById(id);
    }

    @Test
    public void deleteClient_invalidId_throwsException() {
        // given
        Long id = -3L;

        // then
        Mockito.verify(clientRepository, never()).deleteById(id);
        assertThrows(PAIllegalIdException.class, () -> clientService.deleteClient(id));
    }

    @Test
    public void getAll() {
        // given
        Client c1 = new Client("11112222333344445555");
        Client c2 = new Client("66667777888899990000");
        doReturn(List.of(c1, c2)).when(clientRepository).findAll();

        // when
        var res = clientService.getAll();

        // then
        assertEquals(2, res.size());
        Mockito.verify(clientRepository, times(1)).findAll();
    }

    @Test
    public void subtractDiscountPoints_validId_validPoints_throwsNothing() {
        // given
        Client client = new Client("66665555444433332222");
        client.setId(1L);
        client.setDiscountPoints(188);
        int points = 35;
        doNothing().when(clientRepository).subtractDiscountPoints(client.getId(), points);
        doReturn(client).when(clientRepository).getReferenceById(1L);

        // when
        clientService.subtractDiscountPoints(client.getId(), points);

        // then
        Mockito.verify(clientRepository, times(1)).subtractDiscountPoints(client.getId(), points);
    }

    @Test
    public void subtractDiscountPoints_validId_invalidPoints_throwsException() {
        // given
        Client client = new Client("66665555444433332222");
        client.setId(1L);
        client.setDiscountPoints(18);
        doReturn(client).when(clientRepository).getReferenceById(1L);

        // then
        Mockito.verify(clientRepository, never()).subtractDiscountPoints(1L, 35);
        assertThrows(PAIncorrectArgumentException.class, () -> clientService.subtractDiscountPoints(1L, 35));
    }

    @Test
    public void subtractDiscountPoints_invalidId_throwsException() {
        // then
        Mockito.verify(clientRepository, never()).subtractDiscountPoints(-1L, 35);
        assertThrows(PAIllegalIdException.class, () -> clientService.subtractDiscountPoints(-1L, 35));
    }

}