package com.example.demo.serivce.impl;

import com.example.demo.model.*;
import com.example.demo.repository.OrderProductRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.serivce.OrderService;
import com.example.demo.utils.enumeration.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private ProductServiceImpl productServiceImpl;

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
    public Orders save(Orders entity) {

        return null;
    }

    /**
     * Creates orders with products from multiple shops for a specific user.
     * This method groups variants by their associated merchants, creates separate orders for each merchant,
     * and updates the product stock accordingly.
     *
     * @param variants A list of Variant objects representing the products to be ordered.
     *                 Each variant should contain information about the product, quantity, and associated merchant.
     * @param userId   The ID of the user placing the order.
     * @throws RuntimeException if the user with the given ID is not found.
     */
    @Override
    public void createOrderWithProductsFromManyShop(List<Variant> variants, Long userId) {
        HashMap<String, List<Variant>> productMap = new HashMap<>();
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            for (int i = 0; i < variants.size(); i++) {
                List<Variant> firstShopProducts = variants.stream()
                        .filter(variant -> variants.stream().findFirst()
                                .map(v -> v.getProduct().getMerchant().getId())
                                .map(id -> id.equals(variant.getProduct().getMerchant().getId()))
                                .orElse(false))
                        .toList();
                variants.stream().findFirst().ifPresent(v -> 
                    productMap.put(String.valueOf(v.getProduct().getMerchant().getId()), firstShopProducts)
                );
                variants.removeAll(firstShopProducts);
            }
            productMap.forEach((key, value) -> {
                value.stream().findFirst().ifPresent(firstVariant -> {
                    Orders order = new Orders();
                    order.setMerchant(firstVariant.getProduct().getMerchant());
                    order.setUser(user.get());
                    order.setStatus(OrderStatus.PENDING);
                    Orders finalOrder = orderRepository.save(order);
                    value.forEach(variant -> {
                        Double orderPrice = 0.0;
                        OrderProduct orderProduct = new OrderProduct();
                        orderProduct.getProductOrderPK().setOrder(finalOrder);
                        orderProduct.getProductOrderPK().setVariant(variant);
                        orderProduct.setQuantity(variant.getQuantity());
                        orderPrice += orderProduct.getQuantity().doubleValue();
                        orderProductRepository.save(orderProduct);
                        finalOrder.setTotal(finalOrder.getTotal() + orderPrice);
                        productServiceImpl.updateProductStock(variant.getProduct().getId(), variant.getQuantity(), variant.getId());
                    });
                    orderRepository.save(finalOrder);
                });
            });
        }
    }

    @Override
    public List<Orders> findOrdersByUser(Long userId) {
        // Implementation to find all orders for a specific user
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Orders> findOrdersByMerchant(Long merchantId) {
        // Implementation to find all orders for a specific merchant
        return orderRepository.findByMerchantId(merchantId);
    }

    @Override
    public Orders createOrder(Orders order) {
        // Implementation to create a new order
        // You might want to perform validations, calculate total, etc.
        return orderRepository.save(order);
    }

    @Override
    public Orders updateOrderStatus(Long orderId, String newStatus) {
        // Implementation to update the status of an order
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.valueOf(newStatus));
        return orderRepository.save(order);
    }

    @Override
    public List<Orders> findOrdersByDateRange(Date startDate, Date endDate) {
        // Implementation to find orders within a specific date range
        return orderRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public Double calculateTotalRevenueForMerchant(Long merchantId) {
        // Implementation to calculate total revenue for a merchant
        List<Orders> merchantOrders = orderRepository.findByMerchantId(merchantId);
        return merchantOrders.stream()
                .mapToDouble(Orders::getTotal)
                .sum();
    }

    @Override
    public List<Product> findMostOrderedProducts(int limit) {
        // Implementation to find the most frequently ordered products
        // This might require a more complex query or data processing
        // You might need to join with OrderProduct and Product tables
        return null; // Placeholder
    }

    @Override
    public List<Object[]> getDailyRevenueBetweenDates(Date startDate, Date endDate) {
        return orderRepository.getDailyRevenueBetweenDates(startDate, endDate);
    }

    @Override
    public List<Object[]> getMonthlyRevenueBetweenDates(Date startDate, Date endDate) {
        return orderRepository.getMonthlyRevenueBetweenDates(startDate, endDate);
    }

    @Override
    public List<Object[]> getTopMerchantsByRevenue(Date startDate, Date endDate, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return orderRepository.getTopMerchantsByRevenue(startDate, endDate, pageable);
    }

    @Override
    public List<Object[]> getTopProductsByRevenue(Date startDate, Date endDate, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return orderRepository.getTopProductsByRevenue(startDate, endDate, pageable);
    }

    @Override
    public List<Object[]> getRevenueByPriceRange(Date startDate, Date endDate) {
        return orderRepository.getRevenueByPriceRange(startDate, endDate);
    }


    @Override
    public List<Object[]> getProductRevenueAnalysis(Date startDate, Date endDate) {
        return orderRepository.getProductRevenueAnalysis(startDate, endDate);
    }

    @Override
    public List<Object[]> getProductCategoryRevenueAnalysis(Date startDate, Date endDate) {
        return orderRepository.getProductCategoryRevenueAnalysis(startDate, endDate);
    }

    @Override
    public List<Object[]> getMonthlyProductRevenueAnalysis(Date startDate, Date endDate) {
        return orderRepository.getMonthlyProductRevenueAnalysis(startDate, endDate);
    }
}
