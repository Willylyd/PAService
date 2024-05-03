package ru.fev.accumulation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fev.accumulation.entity.CheckPosition;

@Repository
public interface CheckPositionsRepository extends JpaRepository<CheckPosition, Long> {
}
