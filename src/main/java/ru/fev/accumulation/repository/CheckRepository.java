package ru.fev.accumulation.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fev.accumulation.entity.Check;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CheckRepository extends JpaRepository<Check, Long> {

//    @Query
//        ("
//        select ch.*
//        from check ch
//        join client cl on ch.client_id = cl.id
//        where cl.card_number=:cardNumber
//        ")
}
