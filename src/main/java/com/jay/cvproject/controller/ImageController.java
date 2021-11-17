package com.jay.cvproject.controller;

import com.jay.cvproject.mappers.ProductMapper;
import com.jay.cvproject.models.Image;
import com.jay.cvproject.models.Product;
import com.jay.cvproject.service.ImageService;
import com.jay.cvproject.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ImageController(ImageService imageService, ProductService productService, ProductMapper productMapper) {
        this.imageService = imageService;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public Image save(@RequestParam("file") MultipartFile file, @RequestParam String productId) throws IOException {
        Product product = productMapper
                .dtoToEntity(productService.findById(Long.valueOf(productId)));
        return imageService.save(new Image(null, file.getBytes(), product));
    }

    @PostMapping(value = "/bulk-save", consumes = "application/json", produces = "application/json")
    public Iterable<Image> saveAll(@RequestBody List<Image> entities) {
        return imageService.saveAll(entities);
    }

    @GetMapping
    public Iterable<Image> findAll() {
        return imageService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable("id") Long id) {
        Optional<Image> primeMinisterOfIndia = imageService.findById(id);
        byte[] imageBytes = null;
        if (primeMinisterOfIndia.isPresent()) {

            imageBytes = primeMinisterOfIndia.get().getImage();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        imageService.deleteById(id);
    }

}
