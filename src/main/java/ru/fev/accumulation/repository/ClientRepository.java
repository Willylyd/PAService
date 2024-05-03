package ru.fev.accumulation.repository;

import org.springframework.stereotype.Repository;
import ru.fev.accumulation.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
