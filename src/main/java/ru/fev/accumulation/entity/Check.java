package ru.fev.accumulation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@ToString
@Table(name = "order_checks")
@NoArgsConstructor
public class Check {

    public Check(Long clientId) {
        this.clientId = clientId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Setter
    @Column(name = "amount")
    private BigDecimal amount = BigDecimal.valueOf(0);
}
