package com.shop.products.application.ports.inbound.service;

import com.shop.products.application.ports.inbound.FindProductRateUseCase;
import com.shop.products.application.ports.outbound.persistence.ProductRepository;

import java.time.LocalDate;
import java.util.Optional;

import com.shop.products.domain.model.ProductRate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindProductRateService implements FindProductRateUseCase {

    private final ProductRepository productRepository;

    @Override
    public Optional<ProductRate> findProductRate(LocalDate applicationDate, Integer productId, Integer brandId) {
        if(applicationDate == null || productId == null || brandId == null) {
            throw new IllegalArgumentException("applicationDate and productId and brandId must not be null");
        }
        return productRepository.findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(
                        productId, brandId, applicationDate);
    }
}
