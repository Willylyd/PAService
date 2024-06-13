package ru.fev.accumulation.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.fev.accumulation.dto.ClientAndCheckDTO;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.exceptions.PAEntityNotFoundException;
import ru.fev.accumulation.exceptions.PAIllegalIdException;
import ru.fev.accumulation.exceptions.PAIncorrectArgumentException;
import ru.fev.accumulation.mapper.CheckMapper;
import ru.fev.accumulation.repository.CheckRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckServiceImplTest {
    @Mock
    CheckRepository checkRepository;

    @Mock
    CheckMapper checkMapper;

    @InjectMocks
    CheckServiceImpl checkService;

    @Test
    public void addCheck_invalidCheckId_throwsException() {
        // given
        Check check = new Check(-2L, 4L, BigDecimal.valueOf(145));

        // then
        assertThrows(PAIncorrectArgumentException.class, () -> checkService.addCheck(check));
        Mockito.verify(checkRepository, never()).save(check);
    }

    @Test
    public void addCheck_invalidClientId_throwsException() {
        // given
        Check check = new Check(2L, -4L, BigDecimal.valueOf(145));

        // then
        assertThrows(PAIncorrectArgumentException.class, () -> checkService.addCheck(check));
        Mockito.verify(checkRepository, never()).save(check);
    }

    @Test
    public void addCheck_validData_throwsNothing() {
        // given
        Check check = new Check(4L, 3L, BigDecimal.valueOf(115));

        // when
        checkService.addCheck(check);

        // then
        Mockito.verify(checkRepository, times(1)).save(check);
    }

    @Test
    public void addCheck_invalidCheckData_throwsException() {
        // given
        Check check = new Check(1L, 2L, BigDecimal.valueOf(-55));
        doThrow(PAIncorrectArgumentException.class).when(checkRepository).save(check);

        // then
        assertThrows(PAIncorrectArgumentException.class, () -> checkService.addCheck(check));
        Mockito.verify(checkRepository, times(1)).save(check);
    }

    @Test
    public void getById_invalidId_throwsException() {
        // then
        assertThrows(PAIllegalIdException.class, () -> checkService.getById(-4L));
        Mockito.verify(checkRepository, never()).getReferenceById(-4L);
    }

    @Test
    public void getById_validId_throwsNothing() {
        // given
        Check check = new Check(9L, 15L, BigDecimal.valueOf(1540));
        doReturn(check).when(checkRepository).getReferenceById(9L);

        // when
        var res = checkService.getById(9L);

        // then
        assertEquals(res.getAmount(), check.getAmount());
        Mockito.verify(checkRepository, times(1)).getReferenceById(9L);
    }

    @Test
    public void getById_notFound_throwsException() {
        // given
        doThrow(PAEntityNotFoundException.class).when(checkRepository).getReferenceById(51L);

        // then
        assertThrows(PAEntityNotFoundException.class, () -> checkService.getById(51L));
        Mockito.verify(checkRepository, times(1)).getReferenceById(51L);
    }

    @Test
    public void deleteCheck_invalidId_ThrowsException() {
        // then
        assertThrows(PAIllegalIdException.class, () -> checkService.deleteCheck(-4L));
        Mockito.verify(checkRepository, never()).deleteById(-4L);
    }

    @Test
    public void deleteCheck_notFound_ThrowsException() {
        // given
        doThrow(PAEntityNotFoundException.class).when(checkRepository).deleteById(555L);

        // then
        assertThrows(PAEntityNotFoundException.class, () -> checkService.deleteCheck(555L));
        Mockito.verify(checkRepository, times(1)).deleteById(555L);
    }

    @Test
    public void deleteCheck_validData_throwsNothing() {
        // given
        doNothing().when(checkRepository).deleteById(5L);

        // when
        checkService.deleteCheck(5L);

        // then
        Mockito.verify(checkRepository, times(1)).deleteById(5L);
    }

    @Test
    public void getAllByClientId_invalidId_throwsException() {
        // then
        assertThrows(PAIllegalIdException.class, () -> checkService.getAllByClientId(-5L));
        Mockito.verify(checkRepository, never()).getAllByClientId(-5L);
    }

    @Test
    public void getAllByClientId_notFound_throwsException() {
        // given
        doThrow(PAEntityNotFoundException.class).when(checkRepository).getAllByClientId(17L);

        // then
        assertThrows(PAEntityNotFoundException.class, () -> checkService.getAllByClientId(17L));
        Mockito.verify(checkRepository, times(1)).getAllByClientId(17L);
    }

    @Test
    public void getAllByClientId_validData_throwsNothing() {
        // given
        Check c1 = new Check(4L, 17L, BigDecimal.valueOf(590));
        Check c2 = new Check(9L, 17L, BigDecimal.valueOf(1150));
        Check c3 = new Check(22L, 17L, BigDecimal.valueOf(990));
        doReturn(List.of(c1, c2, c3)).when(checkRepository).getAllByClientId(17L);

        // when
        var res = checkService.getAllByClientId(17L);

        // then
        assertEquals(3, res.size());
        Mockito.verify(checkRepository, times(1)).getAllByClientId(17L);
    }

    @Test
    public void getAllByCardNumber_blankCardNumber_throwsException() {
        // then
        assertThrows(PAIncorrectArgumentException.class, () -> checkService.getAllByCardNumber(""));
        Mockito.verify(checkRepository, never()).getAllByCardNumber("");
    }

    @Test
    public void getAllByCardNumber_invalidLength_throwsException() {
        // then
        assertThrows(PAIncorrectArgumentException.class, () -> checkService.getAllByCardNumber("1234567890"));
        Mockito.verify(checkRepository, never()).getAllByCardNumber("1234567890");
    }

    @Test
    public void getAllByCardNumber_validData_throwsNothing() {
        // given
        ClientAndCheckDTO c1 = new ClientAndCheckDTO();
        ClientAndCheckDTO c2 = new ClientAndCheckDTO();
        ClientAndCheckDTO c3 = new ClientAndCheckDTO();
        doReturn(List.of(c1, c2, c3)).when(checkRepository).getAllByCardNumber("11112222333344445555");

        // when
        var res = checkService.getAllByCardNumber("11112222333344445555");

        // then
        assertEquals(3, res.size());
        Mockito.verify(checkRepository, times(1)).getAllByCardNumber("11112222333344445555");
    }

    @Test
    public void getAllTest() {
        // given
        Check c1 = new Check(4L, 6L, BigDecimal.valueOf(1050));
        Check c2 = new Check(15L, 1L, BigDecimal.valueOf(850));
        Check c3 = new Check(21L, 8L, BigDecimal.valueOf(1700));
        doReturn(List.of(c1, c2, c3)).when(checkRepository).findAll();

        // when
        var res = checkService.getAll();

        // then
        assertEquals(3, res.size());
        Mockito.verify(checkRepository, times(1)).findAll();
    }

}