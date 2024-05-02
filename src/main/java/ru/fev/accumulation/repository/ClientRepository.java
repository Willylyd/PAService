package ru.fev.accumulation.repository;

import ru.fev.accumulation.dto.DiscountPointsDTO;
import ru.fev.accumulation.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
