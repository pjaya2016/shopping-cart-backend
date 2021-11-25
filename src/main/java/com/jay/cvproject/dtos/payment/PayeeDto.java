package com.jay.cvproject.dtos.payment;

import lombok.Data;

@Data
public class PayeeDto {
    private String email_address;
    private String merchant_id;
}
