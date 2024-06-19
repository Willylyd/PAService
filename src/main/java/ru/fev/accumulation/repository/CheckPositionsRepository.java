package ru.fev.accumulation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fev.accumulation.entity.CheckPosition;

import java.util.List;

@Repository
public interface CheckPositionsRepository extends JpaRepository<CheckPosition, Long> {

    @Query(value = """
            SELECT *
            FROM check_positions
            WHERE check_id = ?1;
            """
            , nativeQuery = true)
    List<CheckPosition> getAllByCheckId(Long checkId);
}
