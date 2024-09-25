package com.example.demo.serivce.impl;

import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.serivce.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> findAll() {
        return List.of();
    }

    @Override
    public Review findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(Review entity) {

    }
}
