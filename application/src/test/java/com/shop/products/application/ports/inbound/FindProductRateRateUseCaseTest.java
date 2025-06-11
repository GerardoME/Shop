package com.shop.products.application.ports.inbound;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.shop.products.application.ports.inbound.service.FindProductRateService;
import com.shop.products.application.ports.outbound.persistence.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import com.shop.products.domain.model.ProductRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FindProductRateRateUseCaseTest {
    private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private FindProductRateUseCase findProductRateService =
            new FindProductRateService(productRepository);
    private ProductRate productRate;

    @BeforeEach
    public void setUp() {
        productRate = ProductRate.builder()
                .productId(123L)
                .brand("ZARA")
                .curr("EUR")
                .priceList("1")
                .startDate(LocalDateTime.now().minusWeeks(1L))
                .endDate(LocalDateTime.now().plusMonths(1L))
                .priority(1)
                .price(BigDecimal.valueOf(35.50))
                .build();
    }

    @Test
    void findProductRate_withValidParams_ReturnsProductRate() {
        when(productRepository.findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(any(), any(), any()))
                .thenReturn(Optional.of(productRate));
        Optional<ProductRate> productRate = findProductRateService.findProductRate(LocalDate.now(), 12345L, 1L);
        assertNotNull(productRate);
    }
}
