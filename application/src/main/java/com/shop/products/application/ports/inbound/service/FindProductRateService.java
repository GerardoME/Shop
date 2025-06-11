package com.shop.products.application.ports.inbound.service;

import com.shop.products.application.ports.inbound.FindProductRateUseCase;
import com.shop.products.application.ports.outbound.persistence.ProductRepository;

import java.time.LocalDate;
import java.util.Optional;

import com.shop.products.domain.model.ProductRate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindProductRateService implements FindProductRateUseCase {

    private final ProductRepository productRepository;

    @Override
    public Optional<ProductRate> findProductRate(LocalDate applicationDate, Long productId, Long brandId) {

        return productRepository.findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(
                        productId, brandId, applicationDate);
    }
}
