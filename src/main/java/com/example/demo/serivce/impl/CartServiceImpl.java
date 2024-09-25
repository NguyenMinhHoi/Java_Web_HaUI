package com.example.demo.serivce.impl;

import com.example.demo.model.Cart;
import com.example.demo.repository.CartRepository;
import com.example.demo.serivce.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;


    @Override
    public List<Cart> findAll() {
        return List.of();
    }

    @Override
    public Cart findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(Cart entity) {

    }
}
