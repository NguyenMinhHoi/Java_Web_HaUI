package com.example.demo.serivce.impl;

import com.example.demo.model.Orders;
import com.example.demo.repository.OrderRepository;
import com.example.demo.serivce.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Orders> findAll() {
        return List.of();
    }

    @Override
    public Orders findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(Orders entity) {

    }
}
