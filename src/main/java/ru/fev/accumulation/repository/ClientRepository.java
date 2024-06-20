package ru.fev.accumulation.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.fev.accumulation.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client getByCardNumber(String cardNumber);

    @Modifying
    @Query(value = """
            UPDATE clients
            SET discount_points = :new_points
            WHERE id = :id
            """
            , nativeQuery = true)
    void updateDiscountPoints(@Param("id") Long id,
                              @Param("new_points") int newPoints);

    @Modifying
    @Query(value = """
            UPDATE clients
            SET discount_points = discount_points - :pointsToSubtract
            WHERE id = :id
            """
            , nativeQuery = true)
    void subtractDiscountPoints(@Param("id") Long id,
                                @Param("pointsToSubtract") int pointsToSubtract);

    @Query(value = """
            SELECT
                EXISTS(
                SELECT 1
                FROM clients
                WHERE card_number = :card_number)
            """
            , nativeQuery = true)
    boolean existsByCardNumber(@Param("card_number") String cardNumber);
}
