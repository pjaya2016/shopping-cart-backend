package com.jay.cvproject.controller;

import com.jay.cvproject.controller.interfaces.Base;
import com.jay.cvproject.dtos.payment.PaypalPayment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController implements Base {

    @PostMapping("webhook-payment")
    public void getPaymentDetails(@RequestBody PaypalPayment paypalPayment) {
        PaypalPayment p = paypalPayment;

    }

}
