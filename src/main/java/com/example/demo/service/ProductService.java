package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.service.dto.ProductDTO;

import java.util.HashMap;
import java.util.List;

public interface ProductService extends GenerateService<Product>{
    Product createProduct(Product product);

    List<ProductDTO> findAllPage(int page, int size);

    void saveVariants(List<GroupOption> groupOptions,Long productId);
    HashMap<String,Object> getDetailsProducts(Long productId);
    List<Variant> getVariantsByProductId(Long productId);
    void updateVariant(List<Variant> variants);

    Variant getVariantByOption(List<OptionProduct> options);
    List<Product> getAllProductByShopId (Long shopId);

    void addToCart(Long userId, Long productId, int quantity);
    List<Review> getProductReviews(Long productId, int page, int size);
    Product getProductById(Long id);
    List<ProductDTO> getRelatedProducts(Long productId);

    List<Product> searchProducts(String keyword, Long categoryId, Double minPrice, Double maxPrice);
    List<Product> getFeaturedProducts(int limit);
    List<Product> getProductsByCategory(Long categoryId, int page, int size);
    List<Product> getDiscountedProducts(int limit);
    void updateProductStock(Long productId, int quantityChange, Long variantId);



}
