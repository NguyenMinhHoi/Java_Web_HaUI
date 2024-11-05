package com.example.demo.controller;

import com.example.demo.model.Orders;
import com.example.demo.model.Variant;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<List<Orders>> createOrder(@RequestBody HashMap<String,Object> body) {
           List<Orders> orders = orderService.createOrder((List<Variant>) body.get("variants"), (Long) body.get("userId"), (Long) body.get("merchantNumber"));
          return ResponseEntity.ok().body(orders);
    }
}
