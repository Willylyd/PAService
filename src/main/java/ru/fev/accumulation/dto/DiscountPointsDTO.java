package ru.fev.accumulation.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DiscountPointsDTO {
    boolean doSubtract;

    private String cardName;
    private int discountPoints;
}
