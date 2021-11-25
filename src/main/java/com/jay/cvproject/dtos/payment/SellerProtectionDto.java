package com.jay.cvproject.dtos.payment;

import lombok.Data;

import java.util.List;

@Data
public class SellerProtectionDto {
    private String status;
    private List<String> dispute_categories;
}
