package com.shop.products.adapters.inbound;

import com.shop.products.adapters.exceptions.ProductNotFoundException;
import com.shop.products.adapters.inbound.mapper.ApiResponseMapper;
import com.shop.products.adapters.inbound.model.AppliedRate;
import com.shop.products.application.ports.inbound.FindProductRateUseCase;
import com.shop.products.domain.model.ProductRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductsControllerTest {

    private FindProductRateUseCase findProductRateService = mock(FindProductRateUseCase.class);
    private ApiResponseMapper apiResponseMapper = mock(ApiResponseMapper.class);
    private ProductsController productsController = new ProductsController(findProductRateService, apiResponseMapper);

    private OffsetDateTime applicationDate;
    private Integer testProductId;
    private Integer testBrandId;
    private String testBrand;
    private AppliedRate appliedRate;

    @BeforeEach
    void setUp() {
        applicationDate = OffsetDateTime.of(2025, 6, 12, 10, 0, 0, 0, ZoneOffset.UTC);
        testProductId = 35455;
        testBrand = "ZARA";

        appliedRate = new AppliedRate();
        appliedRate.setProductId(testProductId);
        appliedRate.setBrand(testBrand);
        appliedRate.setRateToApply("SUMMER_RATE");
        appliedRate.setApplicationStartDate(OffsetDateTime.parse("2025-06-01T00:00:00Z"));
        appliedRate.setApplicationEndDate(OffsetDateTime.parse("2025-08-31T23:59:59Z"));
        appliedRate.setFinalPriceToApply(35.50F);
    }

    @Test
    void getAppliedRate_shouldReturn200Ok_whenProductFound() {

        when(apiResponseMapper.toAppliedRate(any())).thenReturn(appliedRate);
        when(findProductRateService.findProductRate(any(LocalDateTime.class), any(), any()))
                .thenReturn(Optional.of(mock(ProductRate.class)));

        ResponseEntity<AppliedRate> response =
                productsController.getApplicableRate(applicationDate, testProductId, testBrandId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(appliedRate, response.getBody());
    }

    @Test
    void getAppliedRate_shouldReturn404NotFound_whenProductNotFoundByServiceReturningNull() {
        when(findProductRateService.findProductRate(any(LocalDateTime.class), any(), any()))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(ProductNotFoundException.class, () -> {
                    productsController.getApplicableRate(applicationDate, testProductId, testBrandId);
                });
    }

    @Test
    void getAppliedRate_shouldReturn400BadRequest_whenInvalidApplicationDate() {
        OffsetDateTime invalidDate = OffsetDateTime.of(1999, 6, 12, 10, 0, 0, 0, ZoneOffset.UTC);

        ResponseEntity<AppliedRate> response =
                productsController.getApplicableRate(invalidDate, testProductId, testBrandId);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test()
    void getAppliedRate_shouldReturn500InternalServerError_whenServiceThrowsUnexpectedException() {
        when(findProductRateService.findProductRate(any(LocalDateTime.class), any(), any()))
                .thenThrow(new RuntimeException("Unexpected database error"));
        Assertions.assertThrows(RuntimeException.class, () -> {
            productsController.getApplicableRate(applicationDate, testProductId, testBrandId);
        });
    }
}
