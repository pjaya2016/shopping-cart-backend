package com.jay.cvproject.dtos;

import com.jay.cvproject.enums.ProductType;
import com.jay.cvproject.models.Image;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDto {
    private long id;
    private Date createdDate;
    private String name;
    private String description;
    private int rating;
    private double price;
    private ProductType productType;
    private int stocks;
    private List<String> productImage;
}
