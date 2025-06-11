package com.shop.products.application.ports.outbound.persistence;

import com.shop.products.domain.model.ProductRate;

import java.time.LocalDate;
import java.util.Optional;

public interface ProductRepository {
    Optional<ProductRate> findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(
            Long productId, Long brandId, LocalDate applicationDate);
}
