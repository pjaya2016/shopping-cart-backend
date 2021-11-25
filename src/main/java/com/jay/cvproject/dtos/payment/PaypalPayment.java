package com.jay.cvproject.dtos.payment;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PaypalPayment {
    private Date create_time;
    private Date update_time;
    private String id;
    private String intent;
    private String status;
    private PayerDto payer;
    private List<PurchaseUnitDto> purchase_units;
    private List<LinkDto> links;
}
