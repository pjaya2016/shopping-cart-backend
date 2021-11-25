package com.jay.cvproject.dtos.payment;

import lombok.Data;

@Data
public class ItemDto {
    private String name;
    private UnitAmountDto unit_amount;
    private TaxDto tax;
    private String quantity;
    private String description;
}
