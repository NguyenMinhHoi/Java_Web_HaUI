package com.example.demo.service.impl;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.Variant;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.VariantRepository;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantRepository variantRepository;


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
    public Cart save(Cart entity) {

        return null;
    }

    @Override
    public Cart updateCart(Long userId, Long variantId) {
        Cart cart = cartRepository.findCartByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setId(userId);
        }

        Variant product = variantRepository.findVariantById(variantId);
        if (product != null) {
            cart.getVariants().add(product);
        }

        return cartRepository.save(cart);
    }
}
