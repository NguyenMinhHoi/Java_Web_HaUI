package com.example.demo.serivce;

import com.example.demo.model.Orders;
import com.example.demo.model.Product;
import com.example.demo.model.Variant;

import java.util.Date;
import java.util.List;

public interface OrderService extends GenerateService<Orders>{
    List<Orders> findOrdersByUser(Long userId);
    List<Orders> findOrdersByMerchant(Long merchantId);
    Orders createOrder(Orders order);
    Orders updateOrderStatus(Long orderId, String newStatus);
    List<Orders> findOrdersByDateRange(Date startDate, Date endDate);
    void createOrderWithProductsFromManyShop(List<Variant> products, Long userId);
    Double calculateTotalRevenueForMerchant(Long merchantId);
    List<Product> findMostOrderedProducts(int limit);
    List<Object[]> getDailyRevenueBetweenDates(Date startDate, Date endDate);
    List<Object[]> getMonthlyRevenueBetweenDates(Date startDate, Date endDate);
    List<Object[]> getTopMerchantsByRevenue(Date startDate, Date endDate, int limit);
    List<Object[]> getTopProductsByRevenue(Date startDate, Date endDate, int limit);
    List<Object[]> getRevenueByPriceRange(Date startDate, Date endDate);
    List<Object[]> getProductRevenueAnalysis(Date startDate, Date endDate);
    List<Object[]> getProductCategoryRevenueAnalysis(Date startDate, Date endDate);
    List<Object[]> getMonthlyProductRevenueAnalysis(Date startDate, Date endDate);
}
