package com.jay.cvproject.mappers;

import com.jay.cvproject.dtos.ProductDto;
import com.jay.cvproject.models.Product;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    String IMAGE_URL = "http://localhost:8081/api/v1/image/";

    Product dtoToEntity(ProductDto dto);

    ProductDto entityToDto(Product entity);

    @BeforeMapping
    default void setImageUrlsToDto(Product product, @MappingTarget ProductDto dto) {

        List<String> imgUrls = product.getImages()
                .stream()
                .map(img -> IMAGE_URL + img.getId())
                .collect(Collectors.toList());
        dto.setProductImage(imgUrls);

    }
}
