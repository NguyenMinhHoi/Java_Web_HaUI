package com.example.demo.controller;


import com.example.demo.model.Product;
import com.example.demo.serivce.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Controller
@RequestMapping("/products")
public class ProductController {

      @Autowired
      private ProductService productService;

      @GetMapping("/product/{id}")
      public ResponseEntity<Product> getProductService(@PathVariable Long id) {
            Product product = productService.findById(id);
            return ResponseEntity.ok().body(product);
      }

      @PostMapping("")
      public ResponseEntity<Product> createProduct(@RequestBody Product product) {
             productService.save(product);
             return ResponseEntity.ok().body(product);
      }

      @PutMapping("")
      public ResponseEntity<?> updateProduct(@RequestBody Product product) {
            Product product1 = productService.save(product);
            return ResponseEntity.ok().body(product1);
      }

      @DeleteMapping("/{id}")
      public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
             productService.deleteById(id);
             return ResponseEntity.ok().build();
      }
}
