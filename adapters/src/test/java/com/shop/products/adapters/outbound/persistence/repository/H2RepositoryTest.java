package com.shop.products.adapters.outbound.persistence.repository;

import com.shop.products.adapters.outbound.persistence.BrandEntity;
import com.shop.products.adapters.outbound.persistence.PriceListEntity;
import com.shop.products.adapters.outbound.persistence.PriceRateEntity;
import com.shop.products.adapters.outbound.persistence.PriceRateRepository;
import com.shop.products.adapters.outbound.persistence.mapper.PriceRateMapper;
import com.shop.products.domain.model.ProductRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class H2RepositoryTest {

    private PriceRateRepository priceRateRepository = mock(PriceRateRepository.class);
    private PriceRateMapper priceRateMapper = mock(PriceRateMapper.class);

    private H2Repository h2Repository = new H2Repository(priceRateRepository, priceRateMapper);
    private PriceRateEntity priceRateEntity;

    @BeforeEach
    void setUp() {
        priceRateEntity = new PriceRateEntity();
        priceRateEntity.setId(1L);
        priceRateEntity.setBrand(new BrandEntity(1, "ZARA"));
        priceRateEntity.setProductId(1234);
        priceRateEntity.setPrice(BigDecimal.valueOf(35.05));
        priceRateEntity.setPriceList(new PriceListEntity(1,"SUMMER_SALE"));
        priceRateEntity.setCurr("EUR");
        priceRateEntity.setEndDate(LocalDateTime.now().plusMonths(1));
        priceRateEntity.setStartDate(LocalDateTime.now().minusWeeks(1));
        priceRateEntity.setPriority(1);
    }

    @Test
    void findExistingProduct_ReturnPriceRate() {
        ProductRate mockedProduct = mock(ProductRate.class);
        when(mockedProduct.getPrice()).thenReturn(BigDecimal.valueOf(35.05));
        when(priceRateMapper.toProductRate(priceRateEntity)).thenReturn(mockedProduct);
        when(priceRateRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(eq(1234), eq(1)
                , any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of(priceRateEntity));

        Optional<List<ProductRate>> productRate = h2Repository
                .findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(1234, 1, LocalDateTime.now());

        assertTrue(productRate.isPresent());
        assertEquals(productRate.get().get(0).getPrice(), BigDecimal.valueOf(35.05));
    }


    @Test
    void findExistingProduct_MultipleResults_ReturnMultiplePriceRates() {
        ProductRate mockedProduct = mock(ProductRate.class);
        when(mockedProduct.getPrice()).thenReturn(BigDecimal.valueOf(35.05));
        when(priceRateMapper.toProductRate(priceRateEntity)).thenReturn(mockedProduct);
        when(priceRateRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(eq(1234), eq(1)
                , any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of(priceRateEntity, priceRateEntity));

        Optional<List<ProductRate>> productRate = h2Repository
                .findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(1234, 1, LocalDateTime.now());

        assertTrue(productRate.isPresent());
        assertTrue(productRate.get().size() == 2);
    }

    @Test
    void findExistingProduct_NoResults_ReturnEmptyList() {
        ProductRate mockedProduct = mock(ProductRate.class);
        when(mockedProduct.getPrice()).thenReturn(BigDecimal.valueOf(35.05));
        when(priceRateMapper.toProductRate(priceRateEntity)).thenReturn(mockedProduct);
        when(priceRateRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(eq(1234), eq(1)
                , any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of());

        Optional<List<ProductRate>> productRate = h2Repository
                .findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(1234, 1, LocalDateTime.now());

        assertTrue(productRate.get().isEmpty());
    }
}