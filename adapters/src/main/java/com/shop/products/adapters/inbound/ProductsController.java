package com.shop.products.adapters.inbound;

import com.shop.products.adapters.exceptions.ProductNotFoundException;
import com.shop.products.adapters.inbound.model.AppliedRate;
import com.shop.products.adapters.inbound.mapper.ApiResponseMapper;
import com.shop.products.application.ports.inbound.FindProductRateUseCase;

import java.time.LocalDate;
import java.util.Optional;

import com.shop.products.domain.model.ProductRate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class ProductsController implements ProductsApiDelegate {

    private final FindProductRateUseCase findProductRateUseCase;
    private final ApiResponseMapper apiResponseMapper;

    @Override
    public ResponseEntity<AppliedRate> getApplicableRate(LocalDate applicationDate, Integer productId, Integer brandId) {

        log.info("Request received with date {}, product id {}, brand id {}", applicationDate, productId, brandId);

        if (applicationDate.isBefore(LocalDate.of(2000, 1, 1)) || applicationDate.isAfter(LocalDate.of(2030, 12, 31))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<ProductRate> productRate = findProductRateUseCase.findProductRate(applicationDate, productId, brandId);
        if(productRate.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }
        AppliedRate product = apiResponseMapper.toAppliedRate(productRate.get());

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
