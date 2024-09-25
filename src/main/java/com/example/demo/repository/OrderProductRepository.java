package com.example.demo.repository;

import com.example.demo.model.OrderProduct;
import com.example.demo.model.ProductOrderPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, ProductOrderPK> {
}
