package com.jay.cvproject.controller;

import com.jay.cvproject.controller.interfaces.Base;
import com.jay.cvproject.dtos.ProductDto;
import com.jay.cvproject.dtos.SearchCriteria;
import com.jay.cvproject.enums.ProductType;
import com.jay.cvproject.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController implements Base {

    final private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public List<ProductDto> findAll() {
        return productService.findAll();
    }

    @GetMapping(value = "/products/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping(value = "/product", consumes = "application/json", produces = "application/json")
    public ProductDto save(@RequestBody ProductDto dto) {
        return productService.save(dto);
    }

    @PostMapping("/products/search")
    public List<ProductDto> searchProduct(@RequestBody List<SearchCriteria> searchCriteria) {
        return productService
                .searchProduct(searchCriteria);
    }

    @GetMapping("/product/{id}")
    public ProductDto findById(Long id) {
        return productService.findById(id);
    }

    @GetMapping("/product-type")
    public List<String> getProductTypeColumns() {
        return ProductType
                .stream()
                .map(Enum::name)
                .collect(toList());
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

}
