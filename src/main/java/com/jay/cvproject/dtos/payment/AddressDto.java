package com.jay.cvproject.dtos.payment;

import lombok.Data;

@Data
public class AddressDto {
    private String country_code;
    private String address_line_1;
    private String address_line_2;
    private String admin_area_2;
    private String admin_area_1;
    private String postal_code;
}


/*
public class Address{
    public String country_code;
    public String address_line_1;
    public String address_line_2;
    public String admin_area_2;
    public String admin_area_1;
    public String postal_code;
}

public class Name{
    public String given_name;
    public String surname;
    public String full_name;
}

public class Payer{
    public String email_address;
    public String payer_id;
    public Address address;
    public Name name;
}

public class ItemTotal{
    public String value;
    public String currency_code;
}

public class Shipping{
    public String value;
    public String currency_code;
    public Name name;
    public Address address;
}

public class Handling{
    public String value;
    public String currency_code;
}

public class Insurance{
    public String value;
    public String currency_code;
}

public class ShippingDiscount{
    public String value;
    public String currency_code;
}

public class Breakdown{
    public ItemTotal item_total;
    public Shipping shipping;
    public Handling handling;
    public Insurance insurance;
    public ShippingDiscount shipping_discount;
}

public class Amount{
    public String value;
    public String currency_code;
    public Breakdown breakdown;
}

public class Payee{
    public String email_address;
    public String merchant_id;
}

public class UnitAmount{
    public String value;
    public String currency_code;
}

public class Tax{
    public String value;
    public String currency_code;
}

public class Item{
    public String name;
    public UnitAmount unit_amount;
    public Tax tax;
    public String quantity;
    public String description;
}

public class SellerProtection{
    public String status;
    public List<String> dispute_categories;
}

public class Link{
    public String href;
    public String rel;
    public String method;
    public String title;
}

public class Capture{
    public String status;
    public String id;
    public boolean final_capture;
    public Date create_time;
    public Date update_time;
    public Amount amount;
    public SellerProtection seller_protection;
    public List<Link> links;
}

public class Payments{
    public List<Capture> captures;
}

public class PurchaseUnit{
    public String description;
    public String reference_id;
    public String soft_descriptor;
    public Amount amount;
    public Payee payee;
    public List<Item> items;
    public Shipping shipping;
    public Payments payments;
}

public class Root{
    public Date create_time;
    public Date update_time;
    public String id;
    public String intent;
    public String status;
    public Payer payer;
    public List<PurchaseUnit> purchase_units;
    public List<Link> links;
}

*/