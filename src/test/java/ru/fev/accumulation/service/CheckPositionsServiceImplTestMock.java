package ru.fev.accumulation.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.repository.CheckPositionsRepository;
import ru.fev.accumulation.repository.CheckRepository;
import ru.fev.accumulation.repository.ClientRepository;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CheckPositionsServiceImplTestMock {

    @Mock
    private CheckPositionsRepository checkPositionsRepository;

    @Mock
    private CheckRepository checkRepository;

    @Mock
    private CheckService checkService;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private CheckPositionsServiceImpl checkPositionsService;

    @Test
    void addCheckPosition_testWithNegativeAmount_ShouldThrowException() {
        CheckPosition checkPosition = new CheckPosition();
        checkPosition.setPosAmount(BigDecimal.valueOf(-10));

        assertThrows(InvalidParameterException.class, () -> checkPositionsService.addCheckPosition(checkPosition));
    }

    @Test
    void addCheckPosition_testWithNegativeCheckId_ShouldThrowException() {
        CheckPosition checkPosition = new CheckPosition();
        checkPosition.setCheckId((long)-4);

        assertThrows(NullPointerException.class, () -> checkPositionsService.addCheckPosition(checkPosition));
    }

    @Test
    void addCheckPosition_test() {

        Check check = new Check((long) 3, (long) 5, BigDecimal.valueOf(0));

        when(checkService.getById((long)3)).thenReturn(check);

        CheckPosition checkPosition = new CheckPosition((long) 14, (long) 3, BigDecimal.valueOf(445));

        checkPositionsService.addCheckPosition(checkPosition);

        verify(checkPositionsRepository).save(checkPosition);
    }

    @Test
    void findAll_test() {

        when(checkPositionsRepository.findAll()).thenReturn(Arrays.asList(new CheckPosition(), new CheckPosition()));

        List<CheckPosition> checkPositionList = checkPositionsService.getAll();

        assertEquals(checkPositionList.size(), 2);
        verify(checkPositionsRepository).findAll();
    }

}
