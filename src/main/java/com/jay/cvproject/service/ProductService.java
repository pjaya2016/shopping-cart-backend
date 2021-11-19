package com.jay.cvproject.service;

import com.jay.cvproject.dtos.ProductDto;
import com.jay.cvproject.dtos.SearchCriteria;
import com.jay.cvproject.mappers.ProductMapper;
import com.jay.cvproject.models.Product;
import com.jay.cvproject.repository.ProductRepository;
import com.jay.cvproject.utilities.ProductSearchQueryCriteriaConsumer;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @PersistenceContext
    private EntityManager entityManager;

    public ProductService(ProductRepository productRepository, ProductMapper productMap) {
        this.productRepository = productRepository;
        this.productMapper = productMap;
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

    public List<ProductDto> searchProduct(List<SearchCriteria> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> r = query.from(Product.class);

        Predicate predicate = builder.conjunction();

        ProductSearchQueryCriteriaConsumer searchConsumer =
                new ProductSearchQueryCriteriaConsumer(predicate, builder, r);
        params.forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        List<Product> result = entityManager.createQuery(query).getResultList();
        return result
                .stream()
                .map(productMapper::entityToDto)
                .collect(Collectors.toList());
    }

}
