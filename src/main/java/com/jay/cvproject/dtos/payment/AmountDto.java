package com.jay.cvproject.dtos.payment;

import lombok.Data;

@Data
public class AmountDto {
    private String value;
    private String currency_code;
    private BreakdownDto breakdown;
}
