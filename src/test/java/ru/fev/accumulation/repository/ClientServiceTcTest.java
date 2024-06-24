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
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.service.ClientService;
import ru.fev.accumulation.service.ClientServiceImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("classpath:initDb.sql")
@Testcontainers
public class ClientServiceTcTest {

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", () -> "test");
        registry.add("spring.datasource.password", () -> "test");
    }

    private final ClientService clientService;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceTcTest(ClientRepository clientRepository,
                               CheckRepository checkRepository,
                               CheckPositionsRepository checkPositionsRepository) {
        clientService = new ClientServiceImpl(clientRepository, checkRepository, checkPositionsRepository);
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
        clientService.addClient(new Client("11111111111111111111"));
        clientService.addClient(new Client("22222222222222222222"));
        var result = clientService.getAll();
        assertThat(result.size()).isEqualTo(4);
    }

    @Test
    void getByCardNumberTest() {
        var client = clientService.getById(1L);
        assertThat(client).isNotNull();
        assertThat(client.getCardNumber()).isEqualTo("11112222111122221111");
    }

    @Test
    @Transactional
    void subtractDiscountPointsTest() {
        var clients = clientService.getAll();
        var currPoints = clientService.getDiscountPoints(2L);
        clientService.subtractDiscountPoints(2L, 111);
        clients = clientService.getAll();
//        repository.subtractDiscountPoints(2L, 111);
        var updatedPoints = clientService.getDiscountPoints(2L);
        assertThat(updatedPoints).isEqualTo(currPoints - 111);
    }

    @Test
    void existsByCardNumber() {
        assertThat(clientRepository.existsByCardNumber("35963214785214648529")).isFalse();
        clientService.addClient(new Client("35963214785214648529"));
        assertThat(clientRepository.existsByCardNumber("35963214785214648529")).isTrue();
    }

    @Test
    void addClientTest() {
        var res = clientService.getAll().size();
        clientService.addClient(new Client("12345123451234512345"));
        assertThat(clientService.getAll().size()).isEqualTo(res + 1);
    }

    @Test
    void deleteClientByIdTest() {
        var res = clientService.getAll().size();
        clientService.deleteClient(2L);
        assertThat(clientService.getAll().size()).isEqualTo(res - 1);
    }
}
