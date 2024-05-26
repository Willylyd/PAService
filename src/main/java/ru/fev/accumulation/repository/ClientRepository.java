package ru.fev.accumulation.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.fev.accumulation.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client getByCardNumber(String cardNumber);

    @Modifying
    @Query(value = """
            UPDATE clients
            SET discount_points = ?2
            WHERE id = ?1
            """
            , nativeQuery = true)
    void updateDiscountPoints(Long id, int newPoints);

    @Modifying
    @Query(value = """
            UPDATE clients
            SET discount_points = discount_points - ?2
            WHERE id = ?1
            """
            , nativeQuery = true)
    void subtractDiscountPoints(Long id, int pointsToSubtract);
}
