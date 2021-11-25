package com.jay.cvproject.dtos.payment;

import lombok.Data;

@Data
public class PayerDto {
    public AddressDto address;
    public NameDto name;
    private String email_address;
    private String payer_id;
}

