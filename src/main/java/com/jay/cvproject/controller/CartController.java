package com.jay.cvproject.controller;

import com.jay.cvproject.mappers.ProductMapper;
import com.jay.cvproject.mappers.UserMapper;
import com.jay.cvproject.models.Cart;
import com.jay.cvproject.models.Product;
import com.jay.cvproject.models.auth.User;
import com.jay.cvproject.service.CartService;
import com.jay.cvproject.service.ProductService;
import com.jay.cvproject.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;

    public CartController(CartService cartService, ProductService productService, UserService userService, ProductMapper productMapper, UserMapper userMapper) {
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
        this.productMapper = productMapper;
        this.userMapper = userMapper;
    }

    @PostMapping
    public Cart save(@RequestParam List<Long> productIds, @RequestParam Long userId) {
        Cart entity = new Cart();
        List<Product> products = productIds.stream()
                .map(productService::findById)
                .collect(Collectors.toList())
                .stream()
                .map(productMapper::dtoToEntity)
                .collect(Collectors.toList());

        User user = userMapper.dtoToEntity(userService.findById(userId));
        entity.setProduct(products);
        entity.setUser(user);
        return cartService.save(entity);
    }
}
