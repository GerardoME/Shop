package com.shop.products.adapters.inbound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.shop.products.adapters.exceptions.ProductNotFoundException;
import com.shop.products.adapters.inbound.model.AppliedRate;
//import com.shop.products.adapters.mapper.ApiResponseMapper;
import com.shop.products.adapters.inbound.mapper.ApiResponseMapper;
import com.shop.products.application.ports.inbound.FindProductRateUseCase;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;

import com.shop.products.domain.model.ProductRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ProductsControllerTest {

    private FindProductRateUseCase findProductRateService = mock(FindProductRateUseCase.class);
    private ApiResponseMapper apiResponseMapper = mock(ApiResponseMapper.class);
    private ProductsController productsController = new ProductsController(findProductRateService, apiResponseMapper);

    private LocalDate testDate;
    private Long testProductId;
    private Long testBrandId;
    private AppliedRate appliedRate;

    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(2025, 6, 10);
        testProductId = 35455L;
        testBrandId = 1L;

        appliedRate = new AppliedRate();
        appliedRate.setProductId(testProductId);
        appliedRate.setBrandId(testBrandId);
        appliedRate.setRateToApply("SUMMER_RATE");
        appliedRate.setApplicationStartDate(OffsetDateTime.parse("2025-06-01T00:00:00Z"));
        appliedRate.setApplicationEndDate(OffsetDateTime.parse("2025-08-31T23:59:59Z"));
        appliedRate.setFinalPriceToApply(35.50F);
    }

    @Test
    void getAppliedRate_shouldReturn200Ok_whenProductFound() {

        when(apiResponseMapper.toAppliedRate(any())).thenReturn(appliedRate);
        when(findProductRateService.findProductRate(any(LocalDate.class), any(Long.class), any(Long.class)))
                .thenReturn(Optional.of(mock(ProductRate.class)));

        ResponseEntity<AppliedRate> response =
                productsController.getApplicableRate(testDate, testProductId, testBrandId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(appliedRate, response.getBody());
    }

    @Test
    void getAppliedRate_shouldReturn404NotFound_whenProductNotFoundByServiceReturningNull()
            throws ProductNotFoundException {
        when(findProductRateService.findProductRate(any(LocalDate.class), any(Long.class), any(Long.class)))
                .thenReturn(Optional.empty());
        ResponseEntity<AppliedRate> response =
                productsController.getApplicableRate(testDate, testProductId, testBrandId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void getAppliedRate_shouldReturn400BadRequest_whenInvalidApplicationDate() {
        LocalDate invalidDate = LocalDate.of(1999, 1, 1); // Antes del 2000

        ResponseEntity<AppliedRate> response =
                productsController.getApplicableRate(invalidDate, testProductId, testBrandId);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test()
    void getAppliedRate_shouldReturn500InternalServerError_whenServiceThrowsUnexpectedException() {
        when(findProductRateService.findProductRate(any(LocalDate.class), any(Long.class), any(Long.class)))
                .thenThrow(new RuntimeException("Unexpected database error"));
        Assertions.assertThrows(RuntimeException.class, () -> {
            productsController.getApplicableRate(testDate, testProductId, testBrandId);
        });
    }
}
