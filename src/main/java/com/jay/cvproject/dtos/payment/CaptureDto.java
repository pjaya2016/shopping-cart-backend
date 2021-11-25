package com.jay.cvproject.dtos.payment;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CaptureDto {
    private String status;
    private String id;
    private boolean final_capture;
    private Date create_time;
    private Date update_time;
    private AmountDto amount;
    private SellerProtectionDto seller_protection;
    private List<LinkDto> links;
}
