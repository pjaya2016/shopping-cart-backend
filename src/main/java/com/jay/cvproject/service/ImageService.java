package com.jay.cvproject.service;

import com.jay.cvproject.models.Image;
import com.jay.cvproject.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    public Image save(Image entity) {
        return imageRepository.save(entity);
    }

    public Iterable<Image> saveAll(List<Image> entities) {
        return imageRepository.saveAll(entities);
    }

    public void deleteById(Long id) {
        imageRepository.deleteById(id);
    }


}
