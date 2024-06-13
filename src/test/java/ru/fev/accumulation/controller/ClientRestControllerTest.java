package ru.fev.accumulation.controller;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.fev.accumulation.service.ClientService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ClientRestControllerTest {
    @Mock
    ClientService clientService;

    @Autowired
    private MockMvc mockMvc;
}