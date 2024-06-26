package ru.fev.accumulation.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.exceptions.PAEntityNotFoundException;
import ru.fev.accumulation.exceptions.PAIncorrectArgumentException;
import ru.fev.accumulation.repository.CheckPositionsRepository;
import ru.fev.accumulation.repository.CheckRepository;
import ru.fev.accumulation.repository.ClientRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckPositionsServiceImplTest {

    @Mock
    private CheckPositionsRepository checkPositionsRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CheckRepository checkRepository;

    @InjectMocks
    private CheckServiceImpl checkService;

    @InjectMocks
    private CheckPositionsServiceImpl checkPositionsService;

    @Test
    void addCheckPosition_negativeAmount_throwsException() {
        // given
        CheckPosition checkPosition = new CheckPosition(5L,  new BigDecimal("-10"));

        // then
        assertThrows(PAIncorrectArgumentException.class, () -> checkPositionsService.addCheckPosition(checkPosition));
    }

    @Test
    void addCheckPosition_negativeCheckId_throwsException() {
        // given
        CheckPosition checkPosition = new CheckPosition(-2L, BigDecimal.valueOf(0));

        // then
        assertThrows(PAIncorrectArgumentException.class, () -> checkPositionsService.addCheckPosition(checkPosition));
    }

    @Test
    void addCheckPosition_SuccessTest() {
        // given
        Check check = new Check(3L);
        Check checkSpy = spy(check);
        CheckPosition checkPosition = new CheckPosition(3L, BigDecimal.valueOf(445));
        CheckPosition checkPositionSpy = spy(checkPosition);

        doReturn(checkSpy).when(checkRepository).getReferenceById(3L);
        doNothing().when(checkRepository).increaseAmount(3L, BigDecimal.valueOf(445));
        doReturn(BigDecimal.valueOf(45)).when(checkRepository).getSumOfChecksByClientId(3L);
        doNothing().when(clientRepository).updateDiscountPoints(any(Long.class), any(Integer.class));
        doReturn(true).when(checkRepository).existsById(3L);
        doReturn(3L).when(checkSpy).getId();
        doReturn(BigDecimal.valueOf(445)).when(checkPositionSpy).getPosAmount();

        // when
        checkPositionsService.addCheckPosition(checkPositionSpy);

        // then
        Mockito.verify(checkPositionsRepository, times(1)).save(checkPositionSpy);
    }

    @Test
    void findAll_test() {
        // given
        CheckPosition cp1 = new CheckPosition(81L, BigDecimal.valueOf(550));
        CheckPosition cp2 = new CheckPosition(63L, BigDecimal.valueOf(1100));
        CheckPosition cp3 = new CheckPosition(115L, BigDecimal.valueOf(630));
        doReturn(List.of(cp1, cp2, cp3)).when(checkPositionsRepository).findAll();

        // when
        var checkPositionList = checkPositionsService.getAll();

        // then
        assertEquals(checkPositionList.size(), 3);
        Mockito.verify(checkPositionsRepository, times(1)).findAll();
    }

    @Test
    void deleteCheckPosition_ThrowsException() {
        // given
        CheckPosition checkPosition = new CheckPosition(3L, BigDecimal.valueOf(435));

        // then
        assertThrows(PAEntityNotFoundException.class, () -> checkPositionsService.deleteCheckPosition(-3L));
        Mockito.verify(checkPositionsRepository, never()).deleteById(-3L);
    }

    @Test
    void deleteCheckPosition_Success() {
        // given
        Check check = new Check(42L);
        CheckPosition checkPosition = new CheckPosition(42L, BigDecimal.valueOf(530));
        doReturn(check).when(checkRepository).getReferenceById(42L);
        doReturn(checkPosition).when(checkPositionsRepository).getReferenceById(14L);
        doNothing().when(clientRepository).subtractDiscountPoints(any(Long.class), any(Integer.class));
        doReturn(true).when(checkPositionsRepository).existsById(14L);

        // when
        checkPositionsService.deleteCheckPosition(14L);

        // then
        Mockito.verify(checkPositionsRepository, times(1)).deleteById(14L);
    }

    @Test
    void deleteCheckPosition_invalidId_throwsException() {
        // then
        assertThrows(PAEntityNotFoundException.class, () -> checkPositionsService.deleteCheckPosition(-4L));
        Mockito.verify(checkPositionsRepository, never()).deleteById(-4L);
    }

    @Test
    void getAllByCheckId_validId_throwsNothing() {
        // given
        CheckPosition cp1 = new CheckPosition(3L, BigDecimal.valueOf(155));
        CheckPosition cp2 = new CheckPosition(3L, BigDecimal.valueOf(640));
        CheckPosition cp3 = new CheckPosition(3L, BigDecimal.valueOf(375));
        doReturn(List.of(cp1, cp2, cp3)).when(checkPositionsRepository).getAllByCheckId(3L);
        doReturn(true).when(checkRepository).existsById(3L);

        // when
        var res = checkPositionsService.getAllByCheckId(3L);

        // then
        assertEquals(3, res.size());
        Mockito.verify(checkPositionsRepository, times(1)).getAllByCheckId(3L);
    }

    @Test
    void getAllByCheckId_invalidId_throwsException() {

        // then
        assertThrows(PAEntityNotFoundException.class, () -> checkPositionsService.getAllByCheckId(-3L));
        Mockito.verify(checkPositionsRepository, never()).getAllByCheckId(-3L);
    }

    @Test
    public void getById_validId_throwsNothing() {
        // given
        CheckPosition cp = new CheckPosition(51L, BigDecimal.valueOf(655));
        doReturn(cp).when(checkPositionsRepository).getReferenceById(51L);
        doReturn(true).when(checkPositionsRepository).existsById(51L);

        // when
        var res = checkPositionsService.getById(51L);

        // then
        assertEquals(res.getPosAmount(), cp.getPosAmount());
        Mockito.verify(checkPositionsRepository, times(1)).getReferenceById(51L);
    }

    @Test
    public void getById_invalidId_throwsException() {
        // then
        assertThrows(PAEntityNotFoundException.class, () -> checkPositionsService.getById(-4L));
        Mockito.verify(checkPositionsRepository, never()).getReferenceById(-4L);
    }

    @Test
    public void getById_unknownId_throwsException() {
        // when
        doThrow(PAEntityNotFoundException.class).when(checkPositionsRepository).existsById(611L);

        // then
        assertThrows(PAEntityNotFoundException.class, () -> checkPositionsService.getById(611L));
        Mockito.verify(checkPositionsRepository, never()).getReferenceById(611L);
    }

}
