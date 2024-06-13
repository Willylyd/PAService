package ru.fev.accumulation.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.exceptions.PAIllegalIdException;
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

    @Mock
    private CheckServiceImpl checkService;

    @Mock
    private CheckPositionsServiceImpl checkPositionsService;

    @Test
    void addCheckPosition_testWithNegativeAmount_ShouldThrowException() {
        // given
        CheckPosition checkPosition = new CheckPosition(2L, 5L, BigDecimal.valueOf(-10));

        // then
        assertThrows(PAIncorrectArgumentException.class, () -> checkPositionsService.addCheckPosition(checkPosition));
    }

    @Test
    void addCheckPosition_testWithNegativeCheckId_ShouldThrowException() {
        // given
        CheckPosition checkPosition = new CheckPosition(5L, -2L, BigDecimal.valueOf(0));

        // then
        assertThrows(PAIllegalIdException.class, () -> checkPositionsService.addCheckPosition(checkPosition));
    }

    @Test
    void addCheckPosition_SuccessTest() {
        // given
        Check check = new Check(3L, 5L, BigDecimal.valueOf(0));
        CheckPosition checkPosition = new CheckPosition(14L, 3L, BigDecimal.valueOf(445));

        // when
        when(checkRepository.getReferenceById(3L)).thenReturn(check);
        when(checkRepository.getSumOfChecksByClientId(5L)).thenReturn(BigDecimal.valueOf(445));
        checkPositionsService.addCheckPosition(checkPosition);

        // then
        verify(checkPositionsRepository).save(checkPosition);
    }

    @Test
    void findAll_test() {
        // given
        List<CheckPosition> list = List.of(new CheckPosition(), new CheckPosition(), new CheckPosition());

        // when
        when(checkPositionsRepository.findAll()).thenReturn(list);
        var checkPositionList = checkPositionsService.getAll();

        // then
        assertEquals(checkPositionList.size(), 3);
        verify(checkPositionsService).getAll();
    }

    @Test
    void deleteCheckPosition_ThrowsException() {
        // given
        CheckPosition checkPosition = new CheckPosition(3L, 6L, BigDecimal.valueOf(435));

        // then
        assertThrows(PAIllegalIdException.class, () -> checkPositionsService.deleteCheckPosition(-3L));
    }

    @Test
    void deleteCheckPosition_Success() {
        // given
        CheckPosition checkPosition = new CheckPosition(14L, 42L, BigDecimal.valueOf(530));

        // when
        checkPositionsService.deleteCheckPosition(checkPosition.getId());

        // then
        verify(checkPositionsRepository, times(1)).deleteById(checkPosition.getId());
    }

}
