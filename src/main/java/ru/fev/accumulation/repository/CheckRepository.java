package ru.fev.accumulation.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.fev.accumulation.dto.ClientAndCheckDTO;
import ru.fev.accumulation.entity.Check;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface CheckRepository extends JpaRepository<Check, Long> {
    List<Check> getAllByClientId(Long clientId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE order_checks SET amount = amount + ?2 WHERE id = ?1"
            , nativeQuery = true)
    void increaseAmount(Long checkId, BigDecimal amount);

    @Query(value = "SELECT clients.*, order_checks.* "
            + " FROM clients, order_checks "
            + " WHERE clients.id = order_checks.client_id "
            + " AND clients.card_number = ?1 "
            , nativeQuery = true)
    List<Map<String, Object>> getAllByCardNumber(String cardNumber);
}
