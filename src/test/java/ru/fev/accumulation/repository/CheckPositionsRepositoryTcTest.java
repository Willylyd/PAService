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
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.service.CheckPositionsService;
import ru.fev.accumulation.service.CheckPositionsServiceImpl;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("classpath:initDb.sql")
@Testcontainers
class CheckPositionsRepositoryTcTest {

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", () -> "test");
        registry.add("spring.datasource.password", () -> "test");
    }

    private final CheckPositionsService checkPositionsService;
    private final CheckRepository checkRepository;
    private final ClientRepository clientRepository;
    private final CheckPositionsRepository checkPositionsRepository;

    @Autowired
    public CheckPositionsRepositoryTcTest(CheckPositionsRepository checkPositionsRepository,
                                          CheckRepository checkRepository,
                                          ClientRepository clientRepository) {
        checkPositionsService = new CheckPositionsServiceImpl(checkPositionsRepository, checkRepository, clientRepository);
        this.checkPositionsRepository = checkPositionsRepository;
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
        var result = checkPositionsService.getAll();
        assertThat(result.size()).isEqualTo(7);
    }

    @Test
    void getAllByCheckIdTest() {
        var result = checkPositionsService.getAllByCheckId(1L);
        assertThat(result.size()).isEqualTo(4);
    }

    @Test
    void addCheckPositionTest() {
        var res = checkPositionsService.getAll().size();
        checkPositionsService.addCheckPosition(new CheckPosition(2L, BigDecimal.valueOf(1555)));
        assertThat(checkPositionsService.getAll().size()).isEqualTo(res + 1);
    }

    @Test
    void deleteCheckPositionTest() {
        var res = checkPositionsService.getAll().size();
        checkPositionsService.deleteCheckPosition(3L);
        assertThat(checkPositionsService.getAll().size()).isEqualTo(res - 1);
    }
}