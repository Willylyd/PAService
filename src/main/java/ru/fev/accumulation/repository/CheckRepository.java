package ru.fev.accumulation.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fev.accumulation.entity.Check;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface CheckRepository extends JpaRepository<Check, Long> {
    List<Check> getAllByClientId(Long clientId);

    @Modifying
    @Query(value = """
            UPDATE order_checks
            SET amount = amount + ?2
            WHERE id = ?1
            """
            , nativeQuery = true)
    void increaseAmount(Long checkId, BigDecimal amount);

    @Query(value = """
            SELECT clients.*, order_checks.*
            FROM clients
            JOIN order_checks
            ON clients.id = order_checks.client_id
            WHERE clients.card_number = ?1
            """
            , nativeQuery = true)
    List<Map<String, Object>> getAllByCardNumber(String cardNumber);

    @Query(value = """
            SELECT SUM(amount)
            FROM order_checks
            WHERE client_id = ?1
            """
            , nativeQuery = true)
    BigDecimal getSumOfChecksByClientId(Long clientID);
}
