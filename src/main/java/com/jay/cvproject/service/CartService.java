package com.jay.cvproject.service;

import com.jay.cvproject.models.Cart;
import com.jay.cvproject.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private CartRepository cartRepository;

    public Cart save(Cart entity) {
        return cartRepository.save(entity);
    }
}
