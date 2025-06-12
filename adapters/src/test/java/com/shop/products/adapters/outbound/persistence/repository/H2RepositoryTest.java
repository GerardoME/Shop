package com.shop.products.adapters.outbound.persistence.repository;

import com.shop.products.adapters.outbound.persistence.BrandEntity;
import com.shop.products.adapters.outbound.persistence.PriceRateEntity;
import com.shop.products.adapters.outbound.persistence.PriceRateRepository;
import com.shop.products.adapters.outbound.persistence.mapper.PriceRateMapper;
import com.shop.products.domain.model.ProductRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        priceRateEntity.setBrand(new BrandEntity(1L, BrandEntity.BrandEnum.ZARA.getName()));
        priceRateEntity.setProductId(1234L);
        priceRateEntity.setPrice(BigDecimal.valueOf(35.05));
        priceRateEntity.setPriceList("1");
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
        when(priceRateRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(1234L, 1L, LocalDate.now()
                .atStartOfDay(), LocalDate.now()
                .atStartOfDay())).thenReturn(priceRateEntity);

        Optional<ProductRate> productRate = h2Repository.findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(1234L, 1L, LocalDate.now());

        assertTrue(productRate.isPresent());
        assertEquals(productRate.get().getPrice(), BigDecimal.valueOf(35.05));
    }

}