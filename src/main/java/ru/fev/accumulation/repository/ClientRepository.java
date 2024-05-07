package ru.fev.accumulation.repository;

import org.springframework.stereotype.Repository;
import ru.fev.accumulation.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client getByCardNumber(String cardNumber);
}
