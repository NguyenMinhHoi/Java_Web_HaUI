package com.example.demo.serivce.impl;

import com.example.demo.repository.ImageRepository;
import com.example.demo.serivce.ImageService;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<ImageService> findAll() {
        return List.of();
    }

    @Override
    public ImageService findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(ImageService entity) {

    }
}
