package com.jay.cvproject.controller;

import com.jay.cvproject.controller.interfaces.Base;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController implements Base {


}
