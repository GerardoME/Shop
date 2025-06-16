package com.shop.products.application.ports.outbound.persistence;

import com.shop.products.domain.model.ProductRate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<List<ProductRate>> findByProductIdAndBrandIdAndApplicationDateBetweenStartAndEndDate(
            Integer productId, Integer brandId, LocalDateTime applicationDate);
}
