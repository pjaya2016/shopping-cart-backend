package com.jay.cvproject.dtos.payment;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseUnitDto {
    private String description;
    private String reference_id;
    private String soft_descriptor;
    private AmountDto amount;
    private PayeeDto payee;
    private List<ItemDto> items;
    private ShippingDto shipping;
    private PaymentsDto payments;
}
