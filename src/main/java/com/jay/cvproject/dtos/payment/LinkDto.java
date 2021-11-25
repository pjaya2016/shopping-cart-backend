package com.jay.cvproject.dtos.payment;

import lombok.Data;

@Data
public class LinkDto {
    private String href;
    private String rel;
    private String method;
    private String title;
}
