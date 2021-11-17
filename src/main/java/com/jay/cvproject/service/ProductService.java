package com.jay.cvproject.service;

import com.jay.cvproject.dtos.ProductDto;
import com.jay.cvproject.mappers.ProductMapper;
import com.jay.cvproject.models.Product;
import com.jay.cvproject.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapp) {
        this.productRepository = productRepository;
        this.productMapper = productMapp;
    }

    public List<ProductDto> findAll() {
        return StreamSupport
                .stream(productRepository.findAll().spliterator(), false)
                .map(productMapper::entityToDto)
                .collect(Collectors.toList());

    }

    public ProductDto findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return productMapper.entityToDto(product.get());
        }
        throw new RuntimeException("Id of product was not found");
    }

    public ProductDto save(ProductDto dto) {
        Product entity = productMapper.dtoToEntity(dto);
        return productMapper.entityToDto(productRepository.save(entity));
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
