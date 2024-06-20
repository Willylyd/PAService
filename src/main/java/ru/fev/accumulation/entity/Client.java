package ru.fev.accumulation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Table(name = "clients")
@NoArgsConstructor
public class Client {

    public static final int CARD_NUMBER_LENGTH = 20;

    public Client(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "card_number")
    private String cardNumber;

    @Setter
    @Column(name = "discount_points")
    private int discountPoints = 0;

}
