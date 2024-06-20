package ru.fev.accumulation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@ToString
@Table(name = "check_positions")
@NoArgsConstructor
public class CheckPosition {

    public CheckPosition(Long checkId, BigDecimal posAmount) {
        this.checkId = checkId;
        this.posAmount = posAmount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_id")
    private Long checkId;

    @Column(name = "pos_amount")
    private BigDecimal posAmount;
}
