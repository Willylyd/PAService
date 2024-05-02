package ru.fev.accumulation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fev.accumulation.entity.CheckPosition;

public interface CheckPositionsRepository extends JpaRepository<CheckPosition, Long> {
}
