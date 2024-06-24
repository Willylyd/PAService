package ru.fev.accumulation.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.service.CheckService;
import ru.fev.accumulation.service.CheckServiceImpl;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("classpath:initDb.sql")
@Testcontainers
public class CheckRepositoryTcTest {

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", () -> "test");
        registry.add("spring.datasource.password", () -> "test");
    }

    private final CheckService checkService;
    private final CheckRepository checkRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public CheckRepositoryTcTest(CheckRepository checkRepository,
                                 ClientRepository clientRepository) {
        checkService = new CheckServiceImpl(checkRepository, clientRepository);
        this.checkRepository = checkRepository;
        this.clientRepository = clientRepository;
    }

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @AfterAll
    static void stopContainer() {
        container.stop();
    }

    @Test
    void findAllTest() {
        assertThat(checkRepository.findAll().size()).isEqualTo(3);
        checkService.addCheck(new Check(1L));
        assertThat(checkRepository.findAll().size()).isEqualTo(4);
    }

    @Test
    void getAllByClientIdTest() {
        assertThat(checkService.getAllByClientId(1L).size()).isEqualTo(1);
        checkService.addCheck(new Check(1L));
        assertThat(checkService.getAllByClientId(1L).size()).isEqualTo(2);
    }

    @Test
    void getAllByCardNumberTest() {
        var res = checkService.getAllByCardNumber("11115555999977773333");
        assertThat(res.size()).isEqualTo(2);
        checkService.addCheck(new Check(2L));
        res = checkService.getAllByCardNumber("11115555999977773333");
        assertThat(res.size()).isEqualTo(3);
    }

    @Test
    void getSumOfChecksByClientIdTest() {
        var res = checkRepository.getSumOfChecksByClientId(2L);
        assertThat(res).isEqualTo(BigDecimal.valueOf(3100));
    }
}
