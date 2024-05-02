package ru.fev.accumulation.repository;

import ru.fev.accumulation.entity.Check;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository<Check, Long> {
}
