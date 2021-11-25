package com.jay.cvproject.dtos.payment;

import lombok.Data;

import java.util.List;

@Data
public class PaymentsDto {
    public List<CaptureDto> captures;
}
