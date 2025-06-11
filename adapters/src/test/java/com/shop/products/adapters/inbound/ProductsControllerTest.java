package com.shop.products.adapters.inbound;

import com.shop.products.adapters.exceptions.ProductNotFoundException;
import com.shop.products.adapters.inbound.model.AppliedRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {

    private ProductsController productsController = new ProductsController();

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

        ResponseEntity<AppliedRate> response = productsController.getApplicableRate(testDate, testProductId,
                testBrandId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(appliedRate, response.getBody());
    }

    @Test
    void getAppliedRate_shouldReturn404NotFound_whenProductNotFoundByServiceReturningNull() throws ProductNotFoundException {
        ResponseEntity<AppliedRate> response = productsController.getApplicableRate(testDate, testProductId,
                testBrandId);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void getAppliedRate_shouldReturn400BadRequest_whenInvalidApplicationDate() {
        LocalDate invalidDate = LocalDate.of(1999, 1, 1); // Antes del 2000

        ResponseEntity<AppliedRate> response = productsController.getApplicableRate(invalidDate, testProductId,
                testBrandId);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }


    @Test
    void getAppliedRate_shouldReturn500InternalServerError_whenServiceThrowsUnexpectedException() throws RateNotFoundException {
        when(ProductsController.getRate(any(LocalDate.class), any(Long.class), any(Long.class))).thenThrow(new RuntimeException("Error inesperado en la base de datos"));

        ResponseEntity<AppliedRate> response = productsController.getAppliedRate(testDate, testProductId,
                testBrandId);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}