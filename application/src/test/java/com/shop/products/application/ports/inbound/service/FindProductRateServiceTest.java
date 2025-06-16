package com.shop.products.application.ports.inbound.service;

import com.shop.products.application.ports.outbound.persistence.ProductRepository;
import com.shop.products.domain.model.ProductRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindProductRateServiceTest {

    public static final Integer PRODUCT_ID = 35455;
    public static final double PRODUCT_1_PRICE = 35.50;
    public static final double PRODUCT_2_PRICE = 25.45;
    private final ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private FindProductRateService service = new FindProductRateService(productRepository);

    private ProductRate product1;
    private ProductRate product2;

    @BeforeEach
    void setUp() {
        product1 = ProductRate.builder().productId(PRODUCT_ID).price(BigDecimal.valueOf(PRODUCT_1_PRICE)).priority(0).build();
        product2 = ProductRate.builder().productId(PRODUCT_ID).price(BigDecimal.valueOf(PRODUCT_2_PRICE)).priority(1).build();
    }
    @Test
    void findProductRate_DifferentPriority_ReturnHighestPriorityProductRate() {
        Optional<List<ProductRate>> repositoryProducts = Optional.of(List.of(product1, product2));
        when(productRepository.findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(any(), any(), any())).thenReturn(repositoryProducts);

        Optional<ProductRate> productRate = service.findProductRate(LocalDateTime.now(), PRODUCT_ID, 1);

        verify(productRepository, times(1))
                .findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(any(), any(), any());
        Assertions.assertNotNull(productRate);
        Assertions.assertEquals(productRate.get().getPrice(), BigDecimal.valueOf(PRODUCT_2_PRICE));
    }

    @Test
    void findProductRate_SingleProductResult_ReturnProductRate() {
        Optional<List<ProductRate>> repositoryProducts = Optional.of(List.of(product1));
        when(productRepository.findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(any(), any(), any()))
                .thenReturn(repositoryProducts);

        Optional<ProductRate> productRate = service.findProductRate(LocalDateTime.now(), PRODUCT_ID, 1);

        verify(productRepository, times(1))
                .findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(any(), any(), any());
        Assertions.assertNotNull(productRate);
        Assertions.assertEquals(productRate.get().getPrice(), BigDecimal.valueOf(PRODUCT_1_PRICE));
    }

    @Test
    void findProductRate_EmptyResult_ReturnEmptyProductRate() {
        when(productRepository.findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(any(), any(), any()))
                .thenReturn(Optional.of(List.of()));

        Optional<ProductRate> productRate = service.findProductRate(LocalDateTime.now(), PRODUCT_ID, 1);

        verify(productRepository, times(1))
                .findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(any(), any(), any());
        Assertions.assertNotNull(productRate.isEmpty());
    }

    @Test
    void findProductRate_InvalidParameters_ThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                service.findProductRate(null, 1234, 1));
    }
}