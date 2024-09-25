package com.example.demo.serivce.impl;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.serivce.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return List.of();
    }

    @Override
    public Category findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(Category entity) {

    }
}
