package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.repository.OrderProductRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.OrderServiceImpl;
import com.example.demo.service.impl.ProductServiceImpl;
import com.example.demo.utils.enumeration.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private ProductServiceImpl productServiceImpl;

    @InjectMocks
    private OrderServiceImpl orderService;
    
    
    @Test
void shouldCreateSeparateOrdersForProductsFromDifferentMerchants() {
    // Arrange
    Long userId = 1L;
    User user = new User();
    user.setId(userId);

    Merchant merchant1 = new Merchant();
    merchant1.setId(1L);
    Merchant merchant2 = new Merchant();
    merchant2.setId(2L);

    Product product1 = new Product();
    product1.setId(1L);
    product1.setMerchant(merchant1);
    Product product2 = new Product();
    product2.setId(2L);
    product2.setMerchant(merchant2);

    Variant variant1 = new Variant();
    variant1.setId(1L);
    variant1.setProduct(product1);
    variant1.setQuantity(2);
    Variant variant2 = new Variant();
    variant2.setId(2L);
    variant2.setProduct(product2);
    variant2.setQuantity(3);

    List<Variant> variants = Arrays.asList(variant1, variant2);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(orderRepository.save(any(Orders.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    List<Orders> result = orderService.createOrderWithProductsFromManyShop(variants, userId);

    // Assert
    assertEquals(2, result.size());
    assertEquals(merchant1, result.get(0).getMerchant());
    assertEquals(merchant2, result.get(1).getMerchant());
    assertEquals(user, result.get(0).getUser());
    assertEquals(user, result.get(1).getUser());
    assertEquals(OrderStatus.PENDING, result.get(0).getStatus());
    assertEquals(OrderStatus.PENDING, result.get(1).getStatus());

    verify(orderRepository, times(4)).save(any(Orders.class));
    verify(orderProductRepository, times(2)).save(any(OrderProduct.class));
    verify(productServiceImpl, times(2)).updateProductStock(anyLong(), anyInt(), anyLong());
}
@Test
void shouldCorrectlyGroupVariantsByMerchant() {
    // Arrange
    Long userId = 1L;
    User user = new User();
    user.setId(userId);

    Merchant merchant1 = new Merchant();
    merchant1.setId(1L);
    Merchant merchant2 = new Merchant();
    merchant2.setId(2L);

    Product product1 = new Product();
    product1.setId(1L);
    product1.setMerchant(merchant1);
    Product product2 = new Product();
    product2.setId(2L);
    product2.setMerchant(merchant2);

    Variant variant1 = new Variant();
    variant1.setId(1L);
    variant1.setProduct(product1);
    variant1.setQuantity(2);
    Variant variant2 = new Variant();
    variant2.setId(2L);
    variant2.setProduct(product2);
    variant2.setQuantity(3);

    List<Variant> variants = Arrays.asList(variant1, variant2);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(orderRepository.save(any(Orders.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    List<Orders> result = orderService.createOrderWithProductsFromManyShop(variants, userId);

    // Assert
    assertEquals(2, result.size());
    assertEquals(merchant1, result.get(0).getMerchant());
    assertEquals(merchant2, result.get(1).getMerchant());
    verify(orderRepository, times(2)).save(any(Orders.class));
    verify(orderProductRepository, times(2)).save(any(OrderProduct.class));
    verify(productServiceImpl, times(2)).updateProductStock(anyLong(), anyInt(), anyLong());
}
@Test
void shouldUpdateProductStockAfterCreatingOrders() {
    // Arrange
    Long userId = 1L;
    User user = new User();
    List<Variant> variants = new ArrayList<>();
    Merchant merchant1 = new Merchant();
    merchant1.setId(1L);
    Merchant merchant2 = new Merchant();
    merchant2.setId(2L);

    Product product1 = new Product();
    product1.setId(1L);
    product1.setMerchant(merchant1);
    Variant variant1 = new Variant();
    variant1.setId(1L);
    variant1.setProduct(product1);
    variant1.setQuantity(5);

    Product product2 = new Product();
    product2.setId(2L);
    product2.setMerchant(merchant2);
    Variant variant2 = new Variant();
    variant2.setId(2L);
    variant2.setProduct(product2);
    variant2.setQuantity(3);

    variants.add(variant1);
    variants.add(variant2);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(orderRepository.save(any(Orders.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    List<Orders> result = orderService.createOrderWithProductsFromManyShop(variants, userId);

    // Assert
    assertEquals(2, result.size());
    verify(productServiceImpl).updateProductStock(1L, 5, 1L);
    verify(productServiceImpl).updateProductStock(2L, 3, 2L);
}
@Test
void shouldSetOrderStatusToPendingForNewlyCreatedOrders() {
    // Arrange
    Long userId = 1L;
    User user = new User();
    user.setId(userId);
    
    Merchant merchant1 = new Merchant();
    merchant1.setId(1L);
    
    Product product1 = new Product();
    product1.setId(1L);
    product1.setMerchant(merchant1);
    
    Variant variant1 = new Variant();
    variant1.setId(1L);
    variant1.setProduct(product1);
    variant1.setQuantity(2);
    
    List<Variant> variants = Collections.singletonList(variant1);
    
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(orderRepository.save(any(Orders.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
    // Act
    List<Orders> result = orderService.createOrderWithProductsFromManyShop(variants, userId);
    
    // Assert
    assertFalse(result.isEmpty());
    assertEquals(OrderStatus.PENDING, result.get(0).getStatus());
    verify(orderRepository, times(2)).save(any(Orders.class));
}
@Test
void shouldCalculateAndSetCorrectTotalPriceForEachOrder() {
    // Arrange
    Long userId = 1L;
    User user = new User();
    user.setId(userId);

    Merchant merchant1 = new Merchant();
    merchant1.setId(1L);
    Merchant merchant2 = new Merchant();
    merchant2.setId(2L);

    Product product1 = new Product();
    product1.setId(1L);
    product1.setMerchant(merchant1);
    Product product2 = new Product();
    product2.setId(2L);
    product2.setMerchant(merchant2);

    Variant variant1 = new Variant();
    variant1.setId(1L);
    variant1.setProduct(product1);
    variant1.setQuantity(2);
    variant1.setPrice(10.0);

    Variant variant2 = new Variant();
    variant2.setId(2L);
    variant2.setProduct(product2);
    variant2.setQuantity(3);
    variant2.setPrice(15.0);

    List<Variant> variants = Arrays.asList(variant1, variant2);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(orderRepository.save(any(Orders.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    List<Orders> result = orderService.createOrderWithProductsFromManyShop(variants, userId);

    // Assert
    assertEquals(2, result.size());
    assertEquals(20.0, result.get(0).getTotal(), 0.001);
    assertEquals(45.0, result.get(1).getTotal(), 0.001);

    verify(orderRepository, times(4)).save(any(Orders.class));
    verify(orderProductRepository, times(2)).save(any(OrderProduct.class));
    verify(productServiceImpl, times(2)).updateProductStock(anyLong(), anyInt(), anyLong());
}
@Test
void createOrderWithProductsFromManyShop_EmptyVariantsList_ReturnsEmptyOrderList() {
    // Arrange
    Long userId = 1L;
    List<Variant> emptyVariants = new ArrayList<>();
    User mockUser = new User();
    when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

    // Act
    List<Orders> result = orderService.createOrderWithProductsFromManyShop(emptyVariants, userId);

    // Assert
    assertTrue(result.isEmpty());
    verify(orderRepository, never()).save(any(Orders.class));
    verify(orderProductRepository, never()).save(any(OrderProduct.class));
    verify(productServiceImpl, never()).updateProductStock(anyLong(), anyInt(), anyLong());
}
@Test
void shouldCreateOrdersForAllVariantsEvenWithInvalidMerchantIds() {
    // Arrange
    Long userId = 1L;
    User user = new User();
    user.setId(userId);

    Merchant validMerchant1 = new Merchant();
    validMerchant1.setId(1L);
    Merchant validMerchant2 = new Merchant();
    validMerchant2.setId(2L);

    Product product1 = new Product();
    product1.setId(1L);
    product1.setMerchant(validMerchant1);
    Product product2 = new Product();
    product2.setId(2L);
    product2.setMerchant(validMerchant2);
    Product invalidProduct = new Product();
    invalidProduct.setId(3L);
    invalidProduct.setMerchant(null);

    Variant variant1 = new Variant();
    variant1.setProduct(product1);
    variant1.setQuantity(2);
    Variant variant2 = new Variant();
    variant2.setProduct(product2);
    variant2.setQuantity(3);
    Variant invalidVariant = new Variant();
    invalidVariant.setProduct(invalidProduct);
    invalidVariant.setQuantity(1);

    List<Variant> variants = Arrays.asList(variant1, variant2, invalidVariant);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(orderRepository.save(any(Orders.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    List<Orders> result = orderService.createOrderWithProductsFromManyShop(variants, userId);

    // Assert
    assertEquals(2, result.size());
    verify(orderRepository, times(2)).save(any(Orders.class));
    verify(orderProductRepository, times(2)).save(any(OrderProduct.class));
    verify(productServiceImpl, times(2)).updateProductStock(anyLong(), anyInt(), anyLong());

    // Verify that orders were created for valid variants
    assertTrue(result.stream().anyMatch(order -> order.getMerchant().getId().equals(validMerchant1.getId())));
    assertTrue(result.stream().anyMatch(order -> order.getMerchant().getId().equals(validMerchant2.getId())));

    // Verify that no order was created for the invalid variant
    assertTrue(result.stream().noneMatch(order -> order.getMerchant() == null));
}
@Test
void createOrderWithProductsFromManyShop_shouldHandleZeroQuantityVariants() {
    // Arrange
    Long userId = 1L;
    User user = new User();
    user.setId(userId);

    Merchant merchant1 = new Merchant();
    merchant1.setId(1L);

    Product product1 = new Product();
    product1.setId(1L);
    product1.setMerchant(merchant1);

    Variant variant1 = new Variant();
    variant1.setId(1L);
    variant1.setProduct(product1);
    variant1.setQuantity(0);

    List<Variant> variants = Collections.singletonList(variant1);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(orderRepository.save(any(Orders.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    List<Orders> result = orderService.createOrderWithProductsFromManyShop(variants, userId);

    // Assert
    assertTrue(result.isEmpty());
    verify(orderRepository, never()).save(any(Orders.class));
    verify(orderProductRepository, never()).save(any(OrderProduct.class));
    verify(productServiceImpl, never()).updateProductStock(anyLong(), anyInt(), anyLong());
}
@Test
void shouldProcessOrdersCorrectlyWhenAllVariantsAreFromSameMerchant() {
    // Arrange
    Long userId = 1L;
    Long merchantId = 1L;
    User user = new User();
    user.setId(userId);
    Merchant merchant = new Merchant();
    merchant.setId(merchantId);

    Product product1 = new Product();
    product1.setId(1L);
    product1.setMerchant(merchant);
    Product product2 = new Product();
    product2.setId(2L);
    product2.setMerchant(merchant);

    Variant variant1 = new Variant();
    variant1.setId(1L);
    variant1.setProduct(product1);
    variant1.setQuantity(2);
    Variant variant2 = new Variant();
    variant2.setId(2L);
    variant2.setProduct(product2);
    variant2.setQuantity(3);

    List<Variant> variants = Arrays.asList(variant1, variant2);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(orderRepository.save(any(Orders.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    List<Orders> result = orderService.createOrderWithProductsFromManyShop(variants, userId);

    // Assert
    assertEquals(1, result.size());
    Orders order = result.get(0);
    assertEquals(user, order.getUser());
    assertEquals(merchant, order.getMerchant());
    assertEquals(OrderStatus.PENDING, order.getStatus());

    verify(orderRepository, times(2)).save(any(Orders.class));
    verify(orderProductRepository, times(2)).save(any(OrderProduct.class));
    verify(productServiceImpl, times(2)).updateProductStock(anyLong(), anyInt(), anyLong());
}
}
