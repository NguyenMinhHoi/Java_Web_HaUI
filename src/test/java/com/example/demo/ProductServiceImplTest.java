package com.example.demo;

import com.example.demo.model.GroupOption;
import com.example.demo.model.OptionProduct;
import com.example.demo.model.Product;
import com.example.demo.model.Variant;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.VariantRepository;
import com.example.demo.service.impl.ProductServiceImpl;
import com.example.demo.utils.Const;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private VariantRepository variantRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void shouldSuccessfullyCreateProductAndAssociatedVariant() {
    // Arrange
    Product product = new Product();
    Product savedProduct = new Product();
    savedProduct.setId(1L);
    
    when(productRepository.save(product)).thenReturn(savedProduct);
    
    // Act
    productService.createProduct(product);
    
    // Assert
    verify(productRepository).save(product);
    
    ArgumentCaptor<Variant> variantCaptor = ArgumentCaptor.forClass(Variant.class);
    verify(variantRepository).save(variantCaptor.capture());
    
    Variant capturedVariant = variantCaptor.getValue();
    assertNotNull(capturedVariant);
    assertEquals(savedProduct, capturedVariant.getProduct());
    assertEquals(1, capturedVariant.getOptions().size());
    
    OptionProduct optionProduct = capturedVariant.getOptions().iterator().next();
    assertEquals(Const.DEFAULT, optionProduct.getName());
}
@Test
void shouldUpdateProductGroupOptionsBeforeCreatingVariants() {
    // Arrange
    Long productId = 1L;
    Product product = new Product();
    product.setId(productId);
    List<GroupOption> groupOptions = Arrays.asList(
        new GroupOption(1L, "Color", new HashSet<>(Arrays.asList(
            new OptionProduct(1L, "Red"),
            new OptionProduct(2L, "Blue")
        ))),
        new GroupOption(2L, "Size", new HashSet<>(Arrays.asList(
            new OptionProduct(3L, "Small"),
            new OptionProduct(4L, "Medium")
        ))),
        new GroupOption(3L, "Material", new HashSet<>(Arrays.asList(
                new OptionProduct(5L, "Cotton"),
                new OptionProduct(6L, "Polyester")
        )))
    );

    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    // Act
    productService.saveVariants(groupOptions, productId);

    // Assert
    verify(productRepository).findById(productId);
    verify(productRepository).save(product);
    assertEquals(new HashSet<>(groupOptions), product.getGroupOptions());
    verify(variantRepository).saveAll(anyList());
}
}
